package com.example.fourt.lms_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;

public class login extends AppCompatActivity {
    private static EditText username;
    private static EditText password;
    private static Button login_but;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginButton();
    }

    public void LoginButton()
    {
        username = (EditText)findViewById(R.id.editText);
        password = (EditText)findViewById(R.id.editText2);
        login_but = (Button)findViewById(R.id.button);

        login_but.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(login.this, MainActivity.class);
                        intent.putExtra("userr", username.getText().toString());
                        intent.putExtra("passs", password.getText().toString());
                        startActivity(intent);
                    }
                }
        );
    }

}
