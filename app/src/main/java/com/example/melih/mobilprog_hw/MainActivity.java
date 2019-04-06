package com.example.melih.mobilprog_hw;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etPassword,etUsername;
    Button enterBtn;
    Intent information;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPassword=(EditText)findViewById(R.id.etPassword);
        etUsername=(EditText)findViewById(R.id.etUsername);
        enterBtn=(Button)findViewById(R.id.enterBtn);

        information=new Intent(MainActivity.this,GatherInformation.class);

        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etUsername.getText().toString().equals("admin")&& etPassword.getText().toString().equals("password")){
                    startActivity(information);
                }else{
                    Toast.makeText(MainActivity.this,"Kullanıcı adı veya şifre yanlış",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
