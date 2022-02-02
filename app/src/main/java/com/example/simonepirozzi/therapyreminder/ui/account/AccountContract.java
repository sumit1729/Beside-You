package com.example.simonepirozzi.therapyreminder.ui.account;

import com.example.simonepirozzi.therapyreminder.data.db.model.Account;
import com.example.simonepirozzi.therapyreminder.data.db.model.Task;

public class AccountContract {

    interface Presenter {
        void loadAccount();
        void updateAccount(Account account);
    }

    public interface View{
        void createLayout(Account account);
    }
}
