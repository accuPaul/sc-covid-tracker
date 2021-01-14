package com.paulfolio.chscovidtracker.services;

import com.paulfolio.chscovidtracker.models.CountyStats;
import com.paulfolio.chscovidtracker.models.MetaData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class CountyDataService {

    private static String VIRUS_DATA_URL="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_US.csv";
    private List<CountyStats> countyStats = new ArrayList<>();
    private MetaData metaData = new MetaData();

    public List<CountyStats> getCountyStats() {
        return countyStats;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchData() throws IOException, InterruptedException {
        List<CountyStats> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            StringReader csvBodyReader = new StringReader(response.body());
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
            List<String> headers = new ArrayList<>(records.iterator().next().toMap().keySet());
            metaData.setTodayDate(headers.get(headers.size() - 1));
            for (int i =2; i <= 7; i++)
            {
                metaData.getHeaderDates().add(headers.get(headers.size() - i));
            }

            for (CSVRecord record : records) {
                CountyStats county = new CountyStats();
                String UID = record.get("UID");
                if (!UID.startsWith("84045")) continue;
                county.setCounty(record.get("Admin2"));
                county.setState(record.get("Province_State"));
                county.setLatestTotal(Integer.parseInt(record.get(record.size() -1)));
                county.setNewCases((county.getLatestTotal() - Integer.parseInt(record.get(record.size() -2))));
                for (int i = 2; i <= 7; i++) {
                    county.getNewCasesList().add(Integer.parseInt(record.get(record.size() -i)) - Integer.parseInt(record.get(record.size() -(i+1))));
                }
                newStats.add(county);
            }

            this.countyStats = newStats;
        } catch (Exception e) {
            System.err.println("Could not load data");
            throw e;
        }

    }
}
