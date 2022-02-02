package com.example.simonepirozzi.therapyreminder.ui.login.signin;

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
import com.example.simonepirozzi.therapyreminder.data.db.TinyDB;
import com.example.simonepirozzi.therapyreminder.data.db.model.Account;
import com.example.simonepirozzi.therapyreminder.ui.login.LoginActivity;
import com.example.simonepirozzi.therapyreminder.ui.login.LoginPresenter;
import com.example.simonepirozzi.therapyreminder.ui.main.MainActivity;


public class SigninAcitivty extends AppCompatActivity {
    TextView login;
    EditText name, surname, birthday, mail, username, password;
    Button signin;
    LinearLayout lin;
    TinyDB db;
    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        login = findViewById(R.id.link_login);
        signin = findViewById(R.id.signin_button);
        name = findViewById(R.id.Reg_Name);
        surname = findViewById(R.id.Reg_Sur);
        birthday = findViewById(R.id.Reg_Data);
        mail = findViewById(R.id.Reg_Mail);
        username = findViewById(R.id.Reg_User);
        password = findViewById(R.id.Reg_Pass);
        lin = findViewById(R.id.lreg);
        db = new TinyDB(getApplicationContext());
        mPresenter = new LoginPresenter(this, db);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPresenter.checkSignin(name.getText().toString(), surname.getText().toString(),
                        birthday.getText().toString(), mail.getText().toString(), username.getText().toString(),
                        password.getText().toString())) {

                    mPresenter.createAccount(new Account(name.getText().toString(), surname.getText().toString(), birthday.getText().toString(),
                            mail.getText().toString(), username.getText().toString(), password.getText().toString()));

                    Intent intent = new Intent(SigninAcitivty.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(lin.getContext());
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

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SigninAcitivty.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}
