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
import android.widget.LinearLayout;

import com.example.simonepirozzi.therapyreminder.data.db.TinyDB;
import com.example.simonepirozzi.therapyreminder.data.db.model.Doctor;

import java.util.ArrayList;

public class ModificaMedicoFragment extends Fragment {
    Button modifica,rimuovi;
    EditText nome,cognome,via,telefono;

    TinyDB db;
    ArrayList<Object> medicoArrayList;
    LinearLayout linearLayout;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.edit_fragment_doctor,container,false);

        modifica=view.findViewById(R.id.salvaMedico);
        rimuovi=view.findViewById(R.id.rimuoviMedico);

        nome=view.findViewById(R.id.EditNome);
        cognome=view.findViewById(R.id.EditCognome);
        via=view.findViewById(R.id.EditVia);
        telefono=view.findViewById(R.id.EditTelefono);

        db=new TinyDB(view.getContext());

        final int indice=db.getInt("indiceMedico");
        Doctor med= (Doctor) db.getListObject("keyListaMedico", Doctor.class).get(indice);
        nome.setText(med.getName());
        cognome.setText(med.getSurname());
        telefono.setText(med.getNumber());
        via.setText(med.getAddress());




        modifica.setOnClickListener(new View.OnClickListener() {
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


                    medicoArrayList.remove(indice);
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



        rimuovi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());

                builder.setMessage("Sei sicuro di voler eliminare questo medico?");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(db.getListObject("keyListaMedico", Doctor.class)==null)

                            medicoArrayList=new ArrayList<Object>();

                        else
                            medicoArrayList= db.getListObject("keyListaMedico", Doctor.class);

                        medicoArrayList.remove(indice);

                        db.putListObject("keyListaMedico",medicoArrayList);
                        //vai a terapie fragment
                        FragmentManager fragmentManager=getFragmentManager();
                        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.containerFrame,new MedicoFragment()).commit();
                        fragmentTransaction.addToBackStack(null);
                        dialog.cancel();
                    }
                });

                builder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                Dialog dialog= builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });
        return view;
    }
}
