package edu.handong.csee.isel.model;

public class Logistic {

    private double[] weight;
    private double bias;
    private double learningrate;

    private double[][] training_data;
    private int column_count; //입력 정보들 개수
    private int training_data_count;   // 총 데이터 개수 (총 학생의 수)

    //표준화때 필요한 평균, 표준편차
    private double[] mean;
    private double[] std;

    private int epoch;

    public Logistic() {
        this.epoch = 10;
        this.learningrate = 0.001;
    }

    public Logistic(int epoch, double lr) {
        this.epoch = epoch;
        this.learningrate = lr;
    }

    public void initialize(double[][] data){
        training_data=data;
        column_count=training_data[0].length - 1; //마지막 열은 정답값이니까 빼고 계산함. 그래서 -1함.
        training_data_count=training_data.length;
        weight=new double[column_count];

        for(int i=0;i<column_count;i++){
            weight[i]=0.0;
        }
        bias = 0.0;
        learningrate = 0.001;

        // 표준화를 하기 위해서 평균, 표준편차 배열 만들기
        mean=new double[column_count];
        std=new double[column_count];
    }

    public void standardization(double[][] data){
        // 평균
        for(int i=0;i<column_count;i++){
            double sum=0;
            for(int j=0;j<training_data_count;j++){
                sum+=data[j][i];
            }
            mean[i]=sum/training_data_count;
        }

        // 표준편차
        for(int i=0;i<column_count;i++){
            double vsum=0;
            for(int j=0;j<training_data_count;j++){
                double tmp=data[j][i]-mean[i];
                vsum+=tmp*tmp;
            }
            std[i]=Math.sqrt(vsum/training_data_count);
        }

        //표준화 하기
        for(int i=0;i<training_data_count;i++){
            for(int j=0;j<column_count;j++){
                data[i][j]=(data[i][j]-mean[j])/std[j];
            }
        }
    }

    private double sigmoid(double z){
        if(z>=0){
            double tmp=Math.exp(-z);
            return 1.0/(1.0+tmp);
        } else {
            //z가 엄청 작으면 -z는 큰 양수가 되어버리니까 그냥 z로
            double tmp=Math.exp(z);
            return tmp/(1.0+tmp);
        }
    }

    public void training(double[][] data){
        initialize(data); //초기화
        standardization(training_data); //정규화

        for(int i=0; i<10; i++){
            double loss = 0.0;

            for (int j=0;j<training_data_count;j++) { //총 학생 수 만큼 반복
                double z = 0.0;
                for (int t = 0; t < column_count; t++) { //입력 정보들 만큼 반복
                    z += weight[t] * training_data[j][t]; // z = w·x + b
                }
                z += bias;

                //sigmoid에 z값 넣기 => 0과 1사이의 확률로 z값을 변경해준다.
                double p = sigmoid(z);

                // 정답값
                double correct = training_data[j][column_count];

                // 확률이랑, 정답값이랑 비교를 해 오차를 구한다.
                //loss = −[ylogp+(1−y)log(1−p)]
                loss += -(correct * Math.log(p)+(1.0-correct)*Math.log(1.0-p));

                // error = p - 정답값
                double error = p-correct;

                //가중치 업데이트 식 : w=w−lr(p−y)x
                for (int t = 0; t < column_count; t++) {
                    weight[t] -= learningrate*error * training_data[j][t];
                }
                //bias 업데이트
                bias -= learningrate*error;
            }

            System.out.println("count " + (i + 1) + " loss=" + (loss / training_data_count));
        }
    }
}
