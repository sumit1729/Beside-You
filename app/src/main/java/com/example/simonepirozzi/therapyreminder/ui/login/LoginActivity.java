package com.example.simonepirozzi.therapyreminder.ui.login;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.simonepirozzi.therapyreminder.R;
import com.example.simonepirozzi.therapyreminder.ui.login.signin.SigninAcitivty;
import com.example.simonepirozzi.therapyreminder.data.db.TinyDB;
import com.example.simonepirozzi.therapyreminder.ui.main.MainActivity;

public class LoginActivity extends AppCompatActivity {
    TextView signin, forgetten;
    Button login;
    EditText username, pass;
    LinearLayout linearLayout;
    TinyDB db;
    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signin = findViewById(R.id.link_signin);
        forgetten = findViewById(R.id.link_forgot);
        login = findViewById(R.id.login_button);
        username = findViewById(R.id.Login_Username);
        pass = findViewById(R.id.Login_Password);
        linearLayout = findViewById(R.id.linear);
        db = new TinyDB(getApplicationContext());
        mPresenter = new LoginPresenter(this, db);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SigninAcitivty.class);
                startActivity(intent);
            }
        });

        forgetten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //example
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().length() != 0 && pass.getText().toString().length() != 0) {
                    if (mPresenter.checkLogin(username.getText().toString(), pass.getText().toString())) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(linearLayout.getContext());

                        builder.setMessage(getString(R.string.login_error));
                        builder.setPositiveButton(getString(R.string.positive_button), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        Dialog dialog = builder.create();
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(linearLayout.getContext());
                    builder.setMessage(getString(R.string.login_error_blank));
                    builder.setPositiveButton(getString(R.string.positive_button), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    Dialog dialog = builder.create();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                }
            }
        });
    }
}
