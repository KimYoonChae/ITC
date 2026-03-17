package edu.handong.csee.isel;

import edu.handong.csee.isel.data.CsvReader;
import edu.handong.csee.isel.model.Logistic;
import edu.handong.csee.isel.model.LinearRegression;

public class Main {
    public static void main(String[] args) {
        //args[]는 프로그램 실행 시 외부에서 전달되는 문자열 배열이다.
        // java -jar 자르파일경로.jar --edu.handong.csee.isel.model logistic --epoch 100
        //이렇게 실행하면 args안에 값들이 담긴다.

        //커맨드라인 옵션 처리 하는 이유: 코드를 수정하지 않고, 파라미터값들 바꿀 수 있다.
        //재빌드 없이 실행 인자만 바꾸기가 가능하다.

        // 기본값 설정 (옵션 선택 안했을때)
        String model = "logistic";
        int epoch = 10;
        double lr = 0.001;
        String fileName = null; // 기본값은 모델에 따라 자동 결정

        // args[] 파싱
        // args배열을 순서대로 보면서 옵션 네임, 값 파싱하기
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) { // 옵션 네임 확인하고
                case "--edu.handong.csee.isel.model":
                    model = args[++i]; //전위 연산자로 바로 다음에 들어있는 값 가져오기
                    break;
                case "--epoch":
                    epoch = Integer.parseInt(args[++i]);
                    break;
                case "--lr":
                    lr = Double.parseDouble(args[++i]);
                    break;
                case "--file":
                    fileName = args[++i];
                    break;
                default:
                    System.out.println("알 수 없는 옵션: " + args[i]);
            }
        }

        // 파일명 기본값: 모델에 따라 자동 결정
        if (fileName == null) {
            fileName = model.equals("linear") ? "linear_data.csv" : "logistic_data.csv";
        }

        System.out.println("=== 설정 ===");
        System.out.println("모델     : " + model);
        System.out.println("epoch    : " + epoch);
        System.out.println("lr       : " + lr);
        System.out.println("파일     : " + fileName);
        System.out.println("============");

        CsvReader reader = new CsvReader();
        double[][] data = reader.readCsv(fileName);


        if (model.equals("logistic")) {
            Logistic logisticModel = new Logistic(epoch, lr);
            logisticModel.training(data);

        } else if (model.equals("linear")) {
            LinearRegression linearModel = new LinearRegression(epoch, lr);
            linearModel.training(data);

        } else {
            System.out.println("오류: 알 수 없는 모델 '" + model + "' (logistic 또는 linear 사용)");
        }
    }
}