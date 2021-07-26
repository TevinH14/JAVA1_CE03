package com.example.hamiltontevin_ce03;

// Name: Tevin Hamilton
// Term: JAV1 term 1912
// File Name: MainActivity.java
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private final ArrayList<String> wordsArrayList = new ArrayList<>();
    private Toast mToast = null;
    private NumberPicker mNumberPicker ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNumberPicker = findViewById(R.id.num_numberPicker);
        mNumberPicker.setMinValue(0);
        mNumberPicker.setMaxValue(0);


        findViewById(R.id.add_btn).setOnClickListener(mClickListenerAddWords);
        findViewById(R.id.view_btn).setOnClickListener(mClickListenerViewWords);
    }

    private final View.OnClickListener mClickListenerAddWords = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditText et = findViewById(R.id.input_editText);
            String input = et.getText().toString();


            if(input.contains(" ")){
             if(mToast != null){
                 mToast.cancel();
             }
             mToast = Toast.makeText(MainActivity.this, " Can not Contain spaces",Toast.LENGTH_SHORT);
             mToast.show();
             et.setText("");
            }
            else if(input.isEmpty()){
                if(mToast != null){
                    mToast.cancel();
                }
                mToast = Toast.makeText(MainActivity.this, "cannot leave blank",Toast.LENGTH_SHORT);
                mToast.show();
            }
            else{

                wordsArrayList.add(input);
                GetAvgAndMedian();
                et.setText("");

                String[] numPickerStringArray = new String[wordsArrayList.size()];

                for (int i = 0; i < numPickerStringArray.length; i++) {
                    numPickerStringArray[i] = Integer.toString(i);

                }

                mNumberPicker.setMinValue(0);
                mNumberPicker.setMaxValue(wordsArrayList.size()-1);
                mNumberPicker.setWrapSelectorWheel(true);

            }

        }
    };

    private final View.OnClickListener mClickListenerViewWords = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            NumberPicker numberPicker = findViewById(R.id.num_numberPicker);
           int num =  numberPicker.getValue();
           if(wordsArrayList.size() != 0){
               AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
               builder.setTitle(R.string.selected_word);
               builder.setMessage(wordsArrayList.get(num));
               builder.setIcon(R.drawable.info_icon);
               builder.setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int i) {
                       dialog.cancel();
                   }
               });
               builder.setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       wordsArrayList.remove(0);
                       if(wordsArrayList.size() == 0){
                           mNumberPicker.setMaxValue(0);
                       }  else{
                           mNumberPicker.setMaxValue(wordsArrayList.size()-1);
                       }
                       mNumberPicker.setMinValue(0);
                       mNumberPicker.setWrapSelectorWheel(true);
                       GetAvgAndMedian();
                   }
               });
               builder.show();
           }
        }
    };

    private void GetAvgAndMedian(){
        double avg ;
        double currentLength = 0;
        TextView avgTextView = findViewById(R.id.avg_textView);
        TextView medianTextView =findViewById(R.id.median_textView);
        if(wordsArrayList.size() != 0){
            ArrayList<Double> numArray = new ArrayList<>();
            for (String value: wordsArrayList){
                currentLength += value.length();
                String currentString = String.valueOf(value.length());
                numArray.add(Double.valueOf(currentString));
            }
            avg = currentLength / wordsArrayList.size();
            avgTextView.setText(String.format("%.2f", avg));
            Collections.sort(numArray);
            Double medianDouble;
            if (numArray.size() % 2 == 0) {
                medianDouble = (numArray.get(numArray.size() / 2) + numArray.get(numArray.size() / 2 - 1)) / 2;
                medianTextView.setText(String.format("%.2f", medianDouble));
            }
            else {
                medianDouble = numArray.get(numArray.size() / 2);
                medianTextView.setText(String.format("%.2f", medianDouble));
            }
        }
        else{
            avg = 0;
            currentLength = 0;
            avgTextView.setText(String.format("%.2f", avg));
            medianTextView.setText(String.format("%.2f", currentLength));

        }

    }

}
