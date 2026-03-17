package edu.handong.csee.isel.model;

public class LinearRegression {

    private double weight, bias;
    private double learningrate; //러닝 레이트
    private double[][] training_data;
    private int training_data_count; //데이터 개수

    private int epoch;

    public LinearRegression() {
        this.epoch = 10;
        this.learningrate = 0.001;
    }

    public LinearRegression(int epoch, double lr) {
        this.epoch = epoch;
        this.learningrate = lr;
    }

    public void training(double[][] data) {
        training_data=data;
        learningrate=0.001;
        training_data_count=training_data.length; //데이터 개수
        weight = (training_data[training_data_count - 1][1] - training_data[0][1]) * 1.0 / (training_data[training_data_count - 1][0] - training_data[0][0]);
        //양 끝점(첫번째데이터,마지막데이터)을 지나는 직선의 기울기를 처음weight로 설정
        bias = training_data[0][1] - (weight * training_data[0][0]); // 정해진 w로 맨 처음 데이터를 지나도록

        for (int i = 0; i < 10; i++) {  // 학습 1000번
            update();
        }
        System.out.println("w=" + weight + ", b=" + bias);
    }
    private double predict(double x) {
        return weight * x + bias; //직선 공식
    }
    private void update(){
        double w = 0.0,b=0.0;

        for (int i = 0; i < training_data_count; i++) {
            double x = training_data[i][0],y = training_data[i][1]; //x:입력값,y:정답값
            double error = predict(x)-y; //예측-정답값
            w+=error*x; //오차만큼 가중치 바꾸기
            b+=error;
        }

        w=w/training_data_count;//평균내기
        b=b/training_data_count;

        weight=weight-learningrate*w; //업데이트, -를 해서 기울기 반대방향으로 이동하도록함
        bias=bias-learningrate*b;

    }


}