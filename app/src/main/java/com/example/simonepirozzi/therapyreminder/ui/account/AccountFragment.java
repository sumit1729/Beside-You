package com.example.simonepirozzi.therapyreminder.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.simonepirozzi.therapyreminder.ui.account.edit_account.EditAccountFragment;
import com.example.simonepirozzi.therapyreminder.R;
import com.example.simonepirozzi.therapyreminder.data.db.TinyDB;
import com.example.simonepirozzi.therapyreminder.data.db.model.Account;
import com.example.simonepirozzi.therapyreminder.ui.login.LoginActivity;

public class AccountFragment extends Fragment implements AccountContract.View {
    TextView name, surname, birthday, mail, username, password;
    Button edit, logout;
    TinyDB db;
    private AccountPresenter mPresenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        edit = view.findViewById(R.id.edit_button);
        logout = view.findViewById(R.id.Logout_button);
        name = view.findViewById(R.id.Pro_Name);
        surname = view.findViewById(R.id.Pro_Sur);
        birthday = view.findViewById(R.id.Pro_Data);
        mail = view.findViewById(R.id.Pro_Mail);
        username = view.findViewById(R.id.Pro_User);
        password = view.findViewById(R.id.Pro_Pass);
        db = new TinyDB(view.getContext());
        mPresenter = new AccountPresenter(requireContext(), db, this);
        mPresenter.loadAccount();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerFrame, new EditAccountFragment()).commit();
                fragmentTransaction.addToBackStack(null);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), LoginActivity.class);

                startActivity(intent);
            }
        });
    }

    @Override
    public void createLayout(Account account) {
        name.setText(account.getName());
        surname.setText(account.getSurname());
        birthday.setText(account.getBirthday());
        mail.setText(account.getMail());
        username.setText(account.getUsername());
        password.setText(account.getPassword());
    }
}
