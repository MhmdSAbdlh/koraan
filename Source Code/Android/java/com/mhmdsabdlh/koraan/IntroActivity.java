package com.mhmdsabdlh.koraan;

import static com.mhmdsabdlh.koraan.R.color.lightC;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

public class IntroActivity extends AppCompatActivity {

    ArrayList<Integer> index = new ArrayList<>();
    int[] sowarID = {R.id.btn1,R.id.btn2,R.id.btn3,R.id.btn4,R.id.btn5,R.id.btn6,R.id.btn7,R.id.btn8,
            R.id.btn9,R.id.btn10,R.id.btn11,R.id.btn12,R.id.btn13,R.id.btn14,R.id.btn15,R.id.btn16,
            R.id.btn17,R.id.btn18,R.id.btn19,R.id.btn20,R.id.btn21,R.id.btn22,R.id.btn23,R.id.btn24,
            R.id.btn25,R.id.btn26,R.id.btn27,R.id.btn28,R.id.btn29,R.id.btn30,R.id.btn31,R.id.btn32,
            R.id.btn33,R.id.btn34,R.id.btn35,R.id.btn36,R.id.btn37,R.id.btn38,R.id.btn39,R.id.btn40,
            R.id.btn41,R.id.btn42,R.id.btn43,R.id.btn44,R.id.btn45,R.id.btn46,R.id.btn47,R.id.btn48,
            R.id.btn49,R.id.btn50,R.id.btn51,R.id.btn52,R.id.btn53,R.id.btn54,R.id.btn55,R.id.btn56,
            R.id.btn57,R.id.btn58,R.id.btn59,R.id.btn60,R.id.btn61,R.id.btn62};

    int ui_flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    Button[] sowar = new Button[62];
    ProgressBar pb;
    int pbIndex;
    TextView leftS, doneS, todayS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_main);
        leftS = (TextView)findViewById(R.id.leftSowar);
        todayS = (TextView)findViewById(R.id.todaySora);
        doneS = (TextView)findViewById(R.id.doneSowar);
        for(int i=0; i<62;i++)
            sowar[i] = (Button)findViewById(sowarID[i]);
        openProgress();
    }

    private int readID(int id){
        for(int i=0;i<62;i++)
            if(sowarID[i] == id)
                return i;
        return -1;
    }

    @SuppressLint("ResourceAsColor")
    private void openProgress() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        if(json != null){
            Type type = new TypeToken<ArrayList<Integer>>() {
            }.getType();
            index = gson.fromJson(json, type);
            if (index.size() > 0) {
                for (int i = 0; i < index.size(); i++) {
                    sowar[readID(index.get(i))].setBackgroundColor(lightC);
                    sowar[readID(index.get(i))].setText(sowar[readID(index.get(i))].getText() + "√");
                    sowar[readID(index.get(i))].setEnabled(false);
                }
                progressBar();
                leftS.setText("السور المتبقية:" + (62 - pbIndex));
                doneS.setText("السور المتبقية:" + pbIndex);
                todayS.setText("سورة اليوم: " + sowar[readID(index.get(index.size() - 1))].getText().toString().
                        substring(0, sowar[readID(index.get(index.size() - 1))].length() - 1));
            }
        }
    }

    private void saveProgress() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(index);
        editor.putString("task list", json);
        editor.apply();
    }

    public void clearBtn(View view){
        for(int i=0; i<62;i++)
            if(!sowar[i].isEnabled()){
                sowar[i].setEnabled(true);
                sowar[i].setBackgroundColor(Color.RED);
                sowar[i].setText(sowar[i].getText().toString().substring(0, sowar[i].length()-1));
                progressBar();
                leftS.setText("السور المتبقية:"+(62-pbIndex));
                doneS.setText("السور المنجزة:"+pbIndex);
                todayS.setText("سورة اليوم: غير محدد");
            }
        index.clear();
        saveProgress();
    }

    private void endRead(){
        if(index.size()==62){
            AlertDialog aDialog = new AlertDialog.Builder(this).setTitle("إنتهت ختمية القران")
                    .setMessage("أحسنت, ختمت القرآن الكريم")
                    .setPositiveButton("ختمة جديدة", (dialogInterface, i) -> clearBtn(null))
                    .setNegativeButton("دعاء ختم القرآن", ((dialogInterface, i) -> {
                        Intent intent = new Intent("com.mhmdsabdlh.endRead");
                        startActivity(intent);
                    }))
                    .setNeutralButton("خروج", (dialogInterface, i) -> finish()).create();
            aDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
            aDialog.getWindow().getDecorView().setSystemUiVisibility(ui_flags);
            aDialog.show();
        }

    }

    @SuppressLint("ResourceAsColor")
    public void randomSelection(View view ) {
        if (index.size() < 62) {
            Random random = new Random();
            int randomN = random.nextInt(62);
            while (index.contains(sowarID[randomN]))
                randomN = random.nextInt(62);
            sowar[randomN].setBackgroundColor(lightC);
            sowar[randomN].setText(sowar[randomN].getText() + "√");
            sowar[randomN].setEnabled(false);
            index.add(sowarID[randomN]);
            progressBar();
            leftS.setText("السور المتبقية:"+(62-pbIndex));
            doneS.setText("السور المنجزة:"+pbIndex);
            todayS.setText("سورة اليوم: " + sowar[randomN].getText().toString().substring(0,sowar[randomN].length()-1));
            endRead();
            saveProgress();
        }
    }

    public void undoAction(View view){
        if(index.size()>0){
            Button btn = (Button) findViewById(index.get(index.size()-1));
            btn.setEnabled(true);
            btn.setBackgroundColor(Color.RED);
            btn.setText(btn.getText().toString().substring(0, btn.length()-1));
            progressBar();
            leftS.setText("السور المتبقية:"+(62-pbIndex));
            doneS.setText("السور المنجزة:"+pbIndex);
            index.remove(index.size()-1);
            if(index.size()>0){
                btn = (Button) findViewById(index.get(index.size()-1));
                todayS.setText("سورة اليوم: "+btn.getText().toString().substring(0,btn.length()-1));
            }
            else{
                todayS.setText("سورة اليوم: غير محدد");
            }
            saveProgress();
        }
    }

    public void hideStatusBar() {
        getWindow().getDecorView()
                .setSystemUiVisibility(ui_flags);
    }

    @SuppressLint("ResourceAsColor")
    public void onClick(View view) {

        Button btn = (Button) view;
        todayS.setText("سورة اليوم: "+btn.getText());
        btn.setBackgroundColor(R.color.lightC);
        btn.setText(btn.getText() + "✔");
        btn.setEnabled(false);
        progressBar();
        leftS.setText("السور المتبقية:"+(62-pbIndex));
        doneS.setText("السور المنجزة: "+pbIndex);
        index.add(view.getId());
        endRead();
        saveProgress();
    }

    private void progressBar() {
        pbIndex=0;
        for(int i=0; i<62;i++)
            if(!sowar[i].isEnabled())
                pbIndex++;
        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setMax(62);
        pb.setMin(0);
        pb.setProgress(pbIndex);
        pb.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
    }
}