package kyc.data;

import java.io.*;
import java.util.*;

public class LinearCsvReader {

    private final String fileName;  // 파일 이름

    // 생성자: 파일 이름을 받음
    public LinearCsvReader(String fileName) {
        this.fileName = fileName;
    }

    // CSV 파일을 읽고 int[][] 배열로 반환
    public int[][] readCSV() {
        List<int[]> rows = new ArrayList<>();

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (is == null) {
                throw new RuntimeException("Cannot find file in resources: " + fileName);  // 파일이 없으면 예외 처리
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;

            // 헤더가 있으면 첫 줄 스킵 (예: x, y)
            br.readLine();

            // 파일의 나머지 데이터 읽기
            while ((line = br.readLine()) != null) {
                String[] t = line.trim().split(",");
                int x = Integer.parseInt(t[0].trim());  // 첫 번째 값 x
                int y = Integer.parseInt(t[1].trim());  // 두 번째 값 y
                rows.add(new int[]{x, y});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows.toArray(new int[0][0]);  // List를 int[][] 배열로 변환하여 반환
    }
}