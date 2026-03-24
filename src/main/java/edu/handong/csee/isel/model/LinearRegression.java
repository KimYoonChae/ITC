package edu.handong.csee.isel.model;

public class LinearRegression {

    private double weight, bias;
    private double learningrate; //러닝 레이트
    private double[][] training_data; //학습 데이터 전체
    private int training_data_count; //데이터 개수

    private int epoch; //학습 횟수

    private Backpropagation bp=new Backpropagation(); //역전파(forward, loss 계산, backward) 로직 객체


    //커맨드 라인 옵션에서 입력한 값으로 에폭,러닝레이트 설정하기
    public LinearRegression(int epoch, double lr) {
        this.epoch = epoch;
        this.learningrate = lr;
    }

    public void training(double[][] data) {
        training_data=data;

        training_data_count=training_data.length; //데이터 개수
        weight = (training_data[training_data_count - 1][1] - training_data[0][1]) * 1.0 / (training_data[training_data_count - 1][0] - training_data[0][0]);
        //양 끝점(첫번째데이터,마지막데이터)을 지나는 직선의 기울기를 처음weight로 설정
        bias = training_data[0][1] - (weight * training_data[0][0]); // 정해진 w로 맨 처음 데이터를 지나도록 => 랜덤값보다 데이터 분포에 맞는 초기값으로 설정하면 더빠른 수렴함

        for (int i = 0; i < epoch; i++) {
            update(); //bp없이 실행할 때, bp처럼 단계별로 하지 않고, 오차를 바로 계산하고 업데이트함.
            if ((i + 1) % 10 == 0) {
                System.out.printf("epoch %d  weight=%.4f  bias=%.4f%n", i + 1, weight, bias);
            }
            /*
            bp방식
            double totalloss=0.0;
            double gradientW=0.0;
            double gradientB=0.0;
            for (int j = 0; j < training_data_count; j++) {
                double x=training_data[j][0];
                double y=training_data[j][1];

                double predict=bp.forward(x,weight,bias); //예측값 계산
                double loss=bp.loss(predict,y); //오차 계산
                totalloss+=loss;

                double[] gradients=bp.backward(x,predict,y); //체인룰로 gradient 계산
                gradientW+=gradients[0]; //w의 gradient 누적시키기
                gradientB+=gradients[1]; //b의 gradient  누적시키기
            }
            //각 gradient 평균내고 값들 업데이트
            weight-=learningrate*(gradientW/training_data_count);
            bias-=learningrate*(gradientB/training_data_count);

            double averageLoss = totalloss / training_data_count;
            if((i+1)%10==0){System.out.printf("epoch %d  averageLoss=%.4f  weight=%.4f  bias=%.4f%n",
                    i + 1, averageLoss, weight, bias);}

             */

        }

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