package com.example.simonepirozzi.therapyreminder;

import android.app.Dialog;
import android.content.DialogInterface;
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

import com.example.simonepirozzi.therapyreminder.data.db.TinyDB;
import com.example.simonepirozzi.therapyreminder.data.db.model.Doctor;
import com.example.simonepirozzi.therapyreminder.data.db.model.Task;

import java.util.ArrayList;

public class AggiungiMedicoFragment extends Fragment {
    Button aggiungi;
    EditText nome,cognome,via,telefono;

    TinyDB db;
        ArrayList<Object> medicoArrayList;
    ArrayList<Task> taskArrayList1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.add_fragment_medical,container,false);
        aggiungi=view.findViewById(R.id.addDoctor);
        nome=view.findViewById(R.id.addNome);
        cognome=view.findViewById(R.id.addSurname);
        via=view.findViewById(R.id.addVia);
        telefono=view.findViewById(R.id.addPhone);

        db=new TinyDB(view.getContext());



        aggiungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(nome.getText().toString().length()!=0  && telefono.getText().toString().length()!=0 &&
                        cognome.getText().toString().length()!=0)
                       {



                    if(via.getText().toString()==null){
                        via.setText(" ");
                    }

                    Doctor doctor =new Doctor(nome.getText().toString(),cognome.getText().toString(),telefono.getText().toString(),via.getText().toString());



                    if(db.getListObject("keyListaMedico", Doctor.class)==null)

                        medicoArrayList=new ArrayList<Object>();

                    else
                        medicoArrayList= db.getListObject("keyListaMedico", Doctor.class);


                    medicoArrayList.add(doctor);

                    db.putListObject("keyListaMedico",medicoArrayList);
                    //vai a terapie fragment
                    FragmentManager fragmentManager=getFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerFrame,new MedicoFragment()).commit();
                    fragmentTransaction.addToBackStack(null);


                }
                else{
                    AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());

                    builder.setMessage("Tutti i campi devono essere riempiti");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    Dialog dialog= builder.create();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                }

            }
        });


        return view;
    }
}
