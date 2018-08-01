package com.neurosky.algo_sdk_sample;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Nomalization {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    // 최소값 출력
    public static Double calc_min(Double age[]) {
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
    public static Double calc_max(Double age[]) {
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
    public void min_max(Double age[], double min, double max) {

        double new_min = 20;
        double new_max = 45;
        double result;

        for (int i = 0; i < age.length; i++) {
            result = (((age[i] - min) / (max - min)) * (new_max - new_min)) + new_min;
        }
    }

    // 디비 연동 후 데이터 값 넣으면 됨
    public void main(String[] args) {

        ArrayList<String> result = new ArrayList<>();
        Double[] age = new Double[result.size()];

        for (int i = 0; i < result.size(); i++) {
            age[i] = Double.parseDouble(result.get(i));
        }

        // 최소값, 최대값 찾기
        double min = calc_min(age);
        double max = calc_max(age);

        min_max(age, min, max);
        result.clear();
    }
}
