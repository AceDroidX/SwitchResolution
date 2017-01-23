package com.github.wangxuxin.switchresolution;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    public void aButton(View view){
        set("480","1080","1920",false);
        Toast.makeText(getApplicationContext(), "成功应用1080p分辨率",
                Toast.LENGTH_LONG).show();
    }
    public void bButton(View view){
        set("320","720","1280",false);
        Toast.makeText(getApplicationContext(), "成功应用720p分辨率",
                Toast.LENGTH_LONG).show();
    }
    public void defaultButton(View view){
        set("480","1080","1920",true);
        Toast.makeText(getApplicationContext(), "成功恢复原设置",
                Toast.LENGTH_LONG).show();
    }

    public void setButton(View view){
        EditText dpi_editText = (EditText)findViewById(R.id.dpi_editText);
        EditText s1_editText = (EditText)findViewById(R.id.s1_editText);
        EditText s2_editText = (EditText)findViewById(R.id.s2_editText);
        set(dpi_editText.getText().toString(),s1_editText.getText().toString(),s2_editText.getText().toString(),false);
        Toast.makeText(getApplicationContext(), "成功设置\ndpi"+dpi_editText.getText().toString()+"\n分辨率"+s1_editText.getText().toString()+"x"+s2_editText.getText().toString(),
                Toast.LENGTH_LONG).show();
    }

    private void set(String d, String s1, String s2, boolean m) {
        Runtime mRuntime = Runtime.getRuntime();
        Process mProcess = null;
        try {
            mProcess = mRuntime.exec("su");
            DataOutputStream os = new DataOutputStream(mProcess.getOutputStream());
            os.writeBytes("wm density " + d + " \n");
            if (!m) {
                os.writeBytes("wm size " + s1 + "x" + s2 + " \n");
            }
            else{
                os.writeBytes("wm size reset \n");
            }
            os.writeBytes("exit \n");
            os.flush();
            try {
                mProcess.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
