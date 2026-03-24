package edu.handong.csee.isel.model;

public class Backpropagation {

    //forward(순전파,예측값)
    public double forward (double x, double weight, double bias){
        return weight*x+bias;
        //입력 x * weight + bias ==> 예측값
    }

    //loss (오차)
    public double loss(double predict, double y){
        double err=predict-y;
        return err*err; //오차 제곱 = > 오차 클수록 패널티
    }

    // backward (gradient 계산)
    public double[] backward(double x, double predict, double y) {
        double loss_p= 2.0 * (predict - y);   // loss를 예측값으로 미분한 값
        double loss_w = loss_p * x;               // predict -> weight
        double loss_b = loss_p * 1.0;             //predict -> bias
        return new double[]{loss_w,loss_b};
    }
}
