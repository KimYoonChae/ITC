package edu.handong.csee.isel.data;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CsvReader {

    // fileName을 인자로 받아 CSV를 읽고 double[][] 배열로 반환
    public double[][] readCsv(String fileName) {
        ArrayList<double[]> rows = new ArrayList<>();

        InputStream is = CsvReader.class.getClassLoader().getResourceAsStream(fileName);
        if (is == null) {
            System.err.println("파일을 찾을 수 없습니다: " + fileName);
            return new double[0][0];
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            // 첫 줄 헤더 스킵
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                double[] row = new double[parts.length];

                for (int i = 0; i < parts.length; i++) {
                    row[i] = Double.parseDouble(parts[i].trim()); // int든 double이든 모두 double로 읽음
                }

                rows.add(row);
            }
        } catch (IOException e) {
            System.err.println("파일 읽기 오류: " + e.getMessage());
            return new double[0][0];
        } catch (NumberFormatException e) {
            System.err.println("숫자 변환 오류: " + e.getMessage());
            return new double[0][0];
        }

        return rows.toArray(new double[0][0]);
    }
}