package com.example.simonepirozzi.therapyreminder.ui.account.edit_account;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.simonepirozzi.therapyreminder.R;
import com.example.simonepirozzi.therapyreminder.data.db.TinyDB;
import com.example.simonepirozzi.therapyreminder.data.db.model.Account;
import com.example.simonepirozzi.therapyreminder.ui.account.AccountContract;
import com.example.simonepirozzi.therapyreminder.ui.account.AccountFragment;
import com.example.simonepirozzi.therapyreminder.ui.account.AccountPresenter;
import com.example.simonepirozzi.therapyreminder.ui.login.signin.SigninAcitivty;

public class EditAccountFragment extends Fragment implements AccountContract.View {
    TinyDB db;
    EditText name, surname, birthday, mail, username, password;
    Button edit, delete;
    private AccountPresenter mPresenter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account_edit, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        edit = view.findViewById(R.id.salva_button);
        delete = view.findViewById(R.id.elimina_button);
        name = view.findViewById(R.id.Mpro_Name);
        surname = view.findViewById(R.id.Mpro_Sur);
        birthday = view.findViewById(R.id.Mpro_Data);
        mail = view.findViewById(R.id.Mpro_Mail);
        username = view.findViewById(R.id.Mpro_User);
        password = view.findViewById(R.id.Mpro_Pass);
        db = new TinyDB(view.getContext());
        mPresenter = new AccountPresenter(requireContext(), db, this);
        mPresenter.loadAccount();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().length() != 0 && surname.getText().toString().length() != 0
                        && birthday.getText().toString().length() != 0 && mail.getText().toString().length() != 0
                        && username.getText().toString().length() != 0
                        && password.getText().toString().length() != 0) {

                    mPresenter.updateAccount(new Account(name.getText().toString(), surname.getText().toString(), birthday.getText().toString(),
                            mail.getText().toString(), username.getText().toString(), password.getText().toString()));

                    Toast.makeText(requireContext(), getString(R.string.account_edit_saved), Toast.LENGTH_SHORT);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerFrame, new AccountFragment()).commit();
                    fragmentTransaction.addToBackStack(null);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
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

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

                builder.setMessage(getString(R.string.cancel_account));
                builder.setPositiveButton(getString(R.string.positive_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        db.clear();
                        Intent intent = new Intent(requireContext(), SigninAcitivty.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                Dialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
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
