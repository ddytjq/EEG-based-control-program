package com.neurosky.algo_sdk_sample;

public class Nomalization {
    // 최소값 출력
    public Double calc_min(Double age[]) {
        int i, j;
        Double min = 0.0;
        min = age[0];
        for (i = 1; i < age.length; i++) {
            if (age[i] < min) {
                min = age[i];
            }
        }
        return min;
    }

    // 최대값 뽑기
    public Double calc_max(Double age[]) {
        int i, j;
        Double max = 0.0;
        max = age[0];
        for (i = 0; i < age.length; i++) {
            if (age[i] > max) {
                max = age[i];
            }
        }
        return max;
    }

    // Function to perform min_max normalization
    public void min_max(Double age[], double min, double max, String a) {

        double new_min = 20;
        double new_max = 45;
        double result;

        for (int i = 0; i < age.length; i++) {
            result = (((age[i] - min) / (max - min)) * (new_max - new_min)) + new_min;
        }
    }

    // 디비 연동 후 데이터 값 넣으면 됨
    public static void main(String[] args) {

        Double[] age = new Double[result.size()];


        for (int i = 0; i < result.size(); i++) {
            age[i] = result.elementAt(i);
        }

        // 최소값, 최대값 찾기
        double min = no.calc_min(age);
        double max = no.calc_max(age);

        no.min_max(age, min, max, name2[j]);
        result.clear();
        break;

    }
}
