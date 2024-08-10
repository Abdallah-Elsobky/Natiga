package com.example.natiga;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class data_activity extends AppCompatActivity {

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    TextView tv_name,tv_id,tv_state,tv_degree,tv_percentage;
    ImageView emotion;
    private DatabaseAssetHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_data);

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sp.edit();

        tv_name = findViewById(R.id.data_name);
        tv_id = findViewById(R.id.data_num);
        tv_state = findViewById(R.id.data_state);
        tv_degree = findViewById(R.id.data_degree);
        tv_percentage = findViewById(R.id.data_percent);
        emotion = findViewById(R.id.data_emotion);

        dbHelper = new DatabaseAssetHelper(this);
        try {
            dbHelper.createDatabase();
            dbHelper.openDatabase();


            String studentId,name;
            Cursor cursor;

            if(sp.getBoolean("number",true)){
                studentId = sp.getString("id","error");
                cursor = dbHelper.getStudentDataById(studentId);
            }else{
                name = sp.getString("name","error");
                cursor = dbHelper.getStudentDataByName(name);
            }

            if (cursor.moveToFirst()) {

                String id = cursor.getString(cursor.getColumnIndex("student_id"));
                String sname = cursor.getString(cursor.getColumnIndex("name"));
                float totalDegree = cursor.getFloat(cursor.getColumnIndex("total_degree"));
                String state = cursor.getString(cursor.getColumnIndex("state"));

                float percent = (totalDegree/410) *100;
                String percentage = String.format("%.2f",percent);

                if(!state.equals("ناجح دور أول")){
                    emotion.setImageResource(R.drawable.sadface);
                    Toast.makeText(data_activity.this,"ذاكرو بقى مليتو البلد 😒",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(data_activity.this,"مبروك النجاح 🥳",Toast.LENGTH_SHORT).show();
                }

                tv_name.setText("الاسم : "+sname);
                tv_id.setText("رقم الجلوس : "+id);
                tv_state.setText(state);
                tv_degree.setText("الدرجة : "+totalDegree);
                tv_percentage.setText("النسبة المئوية : "+percentage+" %");
            } else {
                Intent intent = new Intent(data_activity.this,Notfound_Activity.class);
                finish();
                startActivity(intent);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        editor.clear();
        editor.apply();
    }
}