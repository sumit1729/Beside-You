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
import com.example.simonepirozzi.therapyreminder.data.db.model.Examination;

import java.util.ArrayList;

public class ModificaVisiteFragment extends Fragment {
    Button modifica,rimuovi;
    EditText titolo,luogo,giorno,orario,medico;

    TinyDB db;
    ArrayList<Object> visitaArrayList;
    LinearLayout linearLayout;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.edit_fragment_visite,container,false);

        modifica=view.findViewById(R.id.saveVisit);
        rimuovi=view.findViewById(R.id.removeVisit);

        titolo=view.findViewById(R.id.editTitle);
        luogo=view.findViewById(R.id.editPlace);
        giorno=view.findViewById(R.id.editDays);
        orario=view.findViewById(R.id.editHours);
        medico=view.findViewById(R.id.editDoctor);

        db=new TinyDB(view.getContext());

        final int indice=db.getInt("indiceVisita");
        Examination vis= (Examination) db.getListObject("keyListaVisita", Examination.class).get(indice);
        titolo.setText(vis.getTitle());
        luogo.setText(vis.getLocation());
        giorno.setText(vis.getDay());
        orario.setText(vis.getTime());
        medico.setText(vis.getDoctor());




        modifica.setOnClickListener(new View.OnClickListener() {
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



                    visitaArrayList.remove(indice);
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



        rimuovi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());

                builder.setMessage("Sei sicuro di voler eliminare questa visita?");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(db.getListObject("keyListaVisita", Examination.class)==null)

                            visitaArrayList=new ArrayList<Object>();

                        else
                            visitaArrayList= db.getListObject("keyListaVisita", Examination.class);

                        visitaArrayList.remove(indice);

                        db.putListObject("keyListaVisita",visitaArrayList);
                        //vai a terapie fragment
                        FragmentManager fragmentManager=getFragmentManager();
                        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.containerFrame,new VisiteFragment()).commit();
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
