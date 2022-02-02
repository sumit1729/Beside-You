package com.example.simonepirozzi.therapyreminder.ui.account;

import android.content.Context;

import com.example.simonepirozzi.therapyreminder.data.db.TinyDB;
import com.example.simonepirozzi.therapyreminder.data.db.model.Account;
import com.example.simonepirozzi.therapyreminder.utils.Constants;

public class AccountPresenter implements AccountContract.Presenter {

    private Context context;
    private TinyDB db;
    private AccountContract.View view;

    public AccountPresenter(Context context, TinyDB db, AccountContract.View view){
        this.context = context;
        this.db = db;
        this.view = view;
    }

    @Override
    public void loadAccount() {
        Account account = db.getObject(Constants.ACCOUNT_KEY, Account.class);
        view.createLayout(account);
    }

    @Override
    public void updateAccount(Account account) {
        db.putObject(Constants.ACCOUNT_KEY, account);
    }
}
