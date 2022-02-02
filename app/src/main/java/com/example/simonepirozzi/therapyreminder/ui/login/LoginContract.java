package com.example.simonepirozzi.therapyreminder.ui.login;

import com.example.simonepirozzi.therapyreminder.data.db.model.Account;

public interface LoginContract {
    boolean checkLogin(String username, String password);
    boolean checkSignin(String name, String surname,String birthday, String mail,String username, String password);
    void createAccount(Account account);
}
