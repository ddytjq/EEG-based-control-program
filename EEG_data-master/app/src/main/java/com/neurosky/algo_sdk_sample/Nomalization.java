package com.neurosky.algo_sdk_sample;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Nomalization {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("USERS").child("aa").child("EEG DATA");

    private ArrayList<String> result_alpha = new ArrayList<>();
    private ArrayList<String> result_low_beta = new ArrayList<>();
    private ArrayList<String> result_delta = new ArrayList<>();
    private ArrayList<String> result_gamma = new ArrayList<>();
    private ArrayList<String> result_theta = new ArrayList<>();

    private String data[] = new String[5];
    private Double addData[] = new Double[5];

    String str="";

    public Nomalization(final String alpha, final String low_beta, final String delta, final String gamma, final String theta){
        setData();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run(){
                try {

                    Thread.sleep(2000);
                    addData[0] = Double.parseDouble(alpha);
                    addData[1] = Double.parseDouble(low_beta);
                    addData[2] = Double.parseDouble(delta);
                    addData[3] = Double.parseDouble(gamma);
                    addData[4] = Double.parseDouble(theta);

                    Double[] age = new Double[result_alpha.size()+1];
                    age[result_alpha.size()]=addData[0];

                    for (int i = 0; i < result_alpha.size(); i++) {
                        age[i] = Double.parseDouble(result_alpha.get(i));
                    }

                    double min = calc_min(age);
                    double max = calc_max(age);

                    min_max(age, min, max);
                    result_alpha.clear();

                }catch (InterruptedException e){

                }
            }
        });
        thread.start();
    }

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

    public String getData(){
        str=result_alpha.get(0);
        return str;
    }

    public void setData(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int j=0;
                for (int s = 10; s <= 59; s++) {
                    int i=0;
                    for (DataSnapshot snapshot : dataSnapshot.child("2018년").child("07월").child("31일").child("22시").child("19분").child(String.valueOf(s)+"초").getChildren()) {
                        data[i] = snapshot.getValue().toString();
                        i++;
                    }
                    result_alpha.add(data[0]);
                    result_low_beta.add(data[1]);
                    result_delta.add(data[2]);
                    result_gamma.add(data[3]);
                    result_theta.add(data[4]);

//                    Log.d("alpha",result_alpha.get(j));
//                    Log.d("low beta",result_low_beta.get(j));
//                    Log.d("delta",result_delta.get(j));
//                    Log.d("gamma",result_gamma.get(j));
//                    Log.d("theta",result_theta.get(j));
                    j++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    // Function to perform min_max normalization
    public void min_max(Double age[], double min, double max) {

        double new_min = 20;
        double new_max = 45;
        double result[] = new double[age.length];

        for (int i = 0; i < age.length; i++) {
            result[i] = (((age[i] - min) / (max - min)) * (new_max - new_min)) + new_min;
        }
    }

}
