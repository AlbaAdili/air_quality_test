package org.example;

import java.io.*;
import java.util.*;
import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;

public class CSVLoader {
    public static List<SensorData> load(String fileName) throws IOException, CsvValidationException {
        List<SensorData> dataList = new ArrayList<>();

        // Load the file from the classpath (src/main/resources)
        InputStream is = CSVLoader.class.getClassLoader().getResourceAsStream(fileName);
        if (is == null) {
            throw new FileNotFoundException("Resource not found: " + fileName);
        }

        try (CSVReader reader = new CSVReader(new InputStreamReader(is))) {
            String[] line;
            reader.readNext(); // skip header
            while ((line = reader.readNext()) != null) {
                String time = line[0];
                double pm1 = Double.parseDouble(line[33]);
                double pm25 = Double.parseDouble(line[34]);
                double pm10 = Double.parseDouble(line[35]);
                dataList.add(new SensorData(time, pm1, pm25, pm10));
            }
        }

        return dataList;
    }
}
