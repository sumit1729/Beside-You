package com.example.simonepirozzi.therapyreminder.ui.login;

import android.content.Context;
import com.example.simonepirozzi.therapyreminder.data.db.TinyDB;
import com.example.simonepirozzi.therapyreminder.data.db.model.Account;
import com.example.simonepirozzi.therapyreminder.utils.Constants;

public class LoginPresenter implements LoginContract {

    private Context context;
    private TinyDB db;

    public LoginPresenter(Context context, TinyDB db){
        this.context = context;
        this.db = db;
    }


    @Override
    public boolean checkLogin(String username, String password) {
        if (db.getObject(Constants.ACCOUNT_KEY, Account.class) != null) {
            if (username.equalsIgnoreCase(db.getObject(Constants.ACCOUNT_KEY, Account.class).getUsername())
                    && password.equalsIgnoreCase(db.getObject(Constants.ACCOUNT_KEY, Account.class).getPassword())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkSignin(String name, String surname, String birthday, String mail, String username, String password) {
        if (name.length() != 0 && surname.length() != 0
                && birthday.length() != 0 && mail.length() != 0
                && username.length() != 0
                && password.length() != 0){
            return true;
        }
        return false;
    }


    @Override
    public void createAccount(Account account) {
        db.clear();
        db.putObject(Constants.ACCOUNT_KEY, account);
    }
}
