package kyc.data;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class LogisticCsvReader {

    // CSV 파일을 읽고 데이터를 double[][] 배열로 반환
    public double[][] readCsv(String fileName) {
        ArrayList<double[]> rows = new ArrayList<>();

        InputStream is = LogisticCsvReader.class.getClassLoader().getResourceAsStream(fileName);
        if (is == null) {
            System.err.println("Error opening file: " + fileName + " (not found in src/main/resources)");
            return new double[0][0];
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;

            // 1st line header skip
            br.readLine();

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                double[] row = new double[parts.length];

                for (int i = 0; i < parts.length; i++) {
                    String v = parts[i].trim();
                    if (v.isEmpty()) {
                        System.err.println("Error: empty value in row -> " + line);
                        return new double[0][0];
                    }
                    row[i] = Double.parseDouble(v);
                }

                rows.add(row);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return new double[0][0];
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number: " + e.getMessage());
            return new double[0][0];
        }

        double[][] data = new double[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            data[i] = rows.get(i);
        }
        return data;
    }
}