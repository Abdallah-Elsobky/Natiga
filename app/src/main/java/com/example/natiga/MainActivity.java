package com.example.natiga;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button result;
    EditText input;
    RadioButton input_name,input_num;
    String data_type="رقم الجلوس";
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    MediaPlayer click = new MediaPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.Main_btn_data);
        input = findViewById(R.id.Main_et_input);
        input_name = findViewById(R.id.Main_rb_name);
        input_num = findViewById(R.id.Main_rb_num);
        click = MediaPlayer.create(MainActivity.this,R.raw.click3);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sp.edit();

        input_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateClick(view);
                input.setText("");
                input.setHint("ادخل رقم الجلوس");
                input.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(MainActivity.INPUT_METHOD_SERVICE);
                imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                data_type = "رقم الجلوس";
            }
        });
        input_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateClick(view);
                input.setText("");
                input.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(MainActivity.INPUT_METHOD_SERVICE);
                imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setHint("ادخل الاسم كامل");
                data_type = "الاسم كامل";
            }
        });

        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateClick(view);
                if(input.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this,"تأكد ان "+data_type+" صحيح.",Toast.LENGTH_SHORT).show();
                }else{
                    if(input_num.isChecked()){
                        editor.putBoolean("number",true);
                        editor.putString("id",input.getText().toString());
                    }else{
                        editor.putBoolean("number",false);
                        editor.putString("name",input.getText().toString());
                    }
                    editor.apply();
                    animateClick(view);
                    Intent intent = new Intent(MainActivity.this,data_activity.class);
                    startActivity(intent);
                }
            }
        });

    }
    private void animateClick(View view) {
        click.start();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.9f, 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.9f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.setDuration(200);
        animatorSet.start();
    }

}