package kyc;

import kyc.data.LogisticCsvReader;
import kyc.model.Logistic;

import kyc.data.LinearCsvReader;
import kyc.model.LinearRegression;

public class Main {
    public static void main(String[] args) {

        String fileName = "logistic_data.csv";
        // String fileName = "linear_data.csv";

        // CSV 데이터 로드
        LogisticCsvReader reader = new LogisticCsvReader();
        double[][] trainingData = reader.readCsv(fileName);  // CSV 파일을 읽어서 데이터 배열로 반환

        //LinearCsvReader reader = new LinearCsvReader(fileName);
        //int[][] trainingData = reader.readCSV();  // CSV 파일을 읽어서 데이터 배열로 반환

        // 로지스틱 회귀 모델 객체 생성
        Logistic logisticModel = new Logistic();
        // 선형 회귀 모델 객체 생성
        //LinearRegression linearModel = new LinearRegression();

        // 로지스틱 모델 학습 시작
        logisticModel.training(trainingData);  // 모델 학습 실행

        // 선형 모델 학습 시작
        //linearModel.training(trainingData);  // 학습 실행
    }
}






