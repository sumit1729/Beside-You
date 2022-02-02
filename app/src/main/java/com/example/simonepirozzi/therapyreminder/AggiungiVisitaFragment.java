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
import com.example.simonepirozzi.therapyreminder.data.db.model.Examination;
import com.example.simonepirozzi.therapyreminder.data.db.model.Task;

import java.util.ArrayList;

public class AggiungiVisitaFragment extends Fragment {
    Button aggiungi;
    EditText titolo,luogo,giorno,orario,medico;

    TinyDB db;
        ArrayList<Object> visitaArrayList;
    ArrayList<Task> taskArrayList1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.add_fragment_visit,container,false);
        aggiungi=view.findViewById(R.id.addVisita);
        titolo=view.findViewById(R.id.addTitolo);
        luogo=view.findViewById(R.id.addLuogo);
        giorno=view.findViewById(R.id.addGiorno);
        orario=view.findViewById(R.id.addOrario);
        medico=view.findViewById(R.id.addMedicoV);

        db=new TinyDB(view.getContext());



        aggiungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(titolo.getText().toString().length()!=0  && orario.getText().toString().length()!=0 &&
                        giorno.getText().toString().length()!=0)
                       {



                    if(luogo.getText().toString()==null){
                        luogo.setText(" ");
                    }
                    if(medico.getText().toString()==null){
                               medico.setText(" ");
                           }


                    Examination examination =new Examination(titolo.getText().toString(),giorno.getText().toString(),orario.getText().toString(),luogo.getText().toString(),medico.getText().toString());



                    if(db.getListObject("keyListaVisita", Examination.class)==null)

                        visitaArrayList=new ArrayList<Object>();

                    else
                        visitaArrayList= db.getListObject("keyListaVisita", Examination.class);


                    visitaArrayList.add(examination);

                    db.putListObject("keyListaVisita",visitaArrayList);
                    //vai a terapie fragment
                    FragmentManager fragmentManager=getFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerFrame,new VisiteFragment()).commit();
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
