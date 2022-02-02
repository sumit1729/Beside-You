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
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.simonepirozzi.therapyreminder.data.db.TinyDB;
import com.example.simonepirozzi.therapyreminder.data.db.model.Task;
import com.example.simonepirozzi.therapyreminder.ui.therapy.TherapyFragment;

import java.util.ArrayList;

public class ModifcaTerapieFragment extends Fragment {
    Button modifica,rimuovi;
    EditText attivita,numberDurata,orario,note;
    CheckBox lun,mar,mer,gio,ven,sab,dom;
    TinyDB db;
        ArrayList<Object> attivitàArrayList;
    ArrayList<Task> taskArrayList1;
    Task att;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.edit_fragment_terapie,container,false);
        modifica=view.findViewById(R.id.EditTerapia);
        rimuovi=view.findViewById(R.id.RemoveTerapia);
        attivita=view.findViewById(R.id.EditAttivita);
        numberDurata=view.findViewById(R.id.EditnumberDurata);
        orario=view.findViewById(R.id.Editorario);
        note=view.findViewById(R.id.Editnote);
        lun=view.findViewById(R.id.EditcomboLunedi);
        mar=view.findViewById(R.id.EditcomboMartedi);
        mer=view.findViewById(R.id.EditcomboMercoledi);
        gio=view.findViewById(R.id.EditcomboGiovedi);
        ven=view.findViewById(R.id.EditcomboVenerdi);
        sab=view.findViewById(R.id.EditcomboSabato);
        dom=view.findViewById(R.id.EditcomboDomenica);
        db=new TinyDB(view.getContext());
        final int indice=db.getInt("indice");
         att= (Task) db.getListObject("keyListaAttivita", Task.class).get(indice);
        attivita.setText(att.getTask());
        numberDurata.setText(att.getDuration()+"");
        orario.setText(att.getTime());
        note.setText(att.getNote());
        for(int i=0;i<att.getListDays().size();i++){
            if(att.getListDays().get(i).equalsIgnoreCase("lunedi"))  lun.setChecked(true);
            if(att.getListDays().get(i).equalsIgnoreCase("martedi"))  mar.setChecked(true);
            if(att.getListDays().get(i).equalsIgnoreCase("mercoledi"))  mer.setChecked(true);
            if(att.getListDays().get(i).equalsIgnoreCase("giovedi"))  gio.setChecked(true);
            if(att.getListDays().get(i).equalsIgnoreCase("venerdi"))  ven.setChecked(true);
            if(att.getListDays().get(i).equalsIgnoreCase("sabato"))  sab.setChecked(true);
            if(att.getListDays().get(i).equalsIgnoreCase("domenica"))  dom.setChecked(true);

        }



        modifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(attivita.getText().toString().length()!=0  && numberDurata.getText().toString().length()!=0 &&
                        (lun.isChecked() || mar.isChecked() || mer.isChecked()|| gio.isChecked() || ven.isChecked() ||
                        sab.isChecked() || dom.isChecked()) && orario.getText().toString()!=null){

                    //inserisci attivita prima in lista di oggetti attivita e poi in tiny db con chiave keyListaAttivita
                    ArrayList<String> arrayList=new ArrayList<>();
                    if(lun.isChecked())     arrayList.add("lunedi");
                    if(mar.isChecked())     arrayList.add("martedi");
                    if(mer.isChecked())     arrayList.add("mercoledi");
                    if(gio.isChecked())     arrayList.add("giovedi");
                    if(ven.isChecked())     arrayList.add("venerdi");
                    if(sab.isChecked())     arrayList.add("sabato");
                    if(dom.isChecked())     arrayList.add("domenica");


                    if(note.getText().toString()==null){
                        note.setText(" ");
                    }

                    Task task =new Task(attivita.getText().toString(),orario.getText().toString(),note.getText().toString(),
                            arrayList,numberDurata.getText().toString(),att.getDate());



                    if(db.getListObject("keyListaAttivita", Task.class)==null)

                        attivitàArrayList=new ArrayList<Object>();

                    else
                        attivitàArrayList= db.getListObject("keyListaAttivita", Task.class);

                    attivitàArrayList.remove(indice);
                    attivitàArrayList.add(task);


                    db.putListObject("keyListaAttivita",attivitàArrayList);
                    //vai a terapie fragment
                    FragmentManager fragmentManager=getFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerFrame,new TherapyFragment()).commit();
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

                builder.setMessage("Sei sicuro di voler eliminare questa terapia?");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(db.getListObject("keyListaAttivita", Task.class)==null)

                            attivitàArrayList=new ArrayList<Object>();

                        else
                            attivitàArrayList= db.getListObject("keyListaAttivita", Task.class);

                        attivitàArrayList.remove(indice);

                        db.putListObject("keyListaAttivita",attivitàArrayList);
                        //vai a terapie fragment
                        FragmentManager fragmentManager=getFragmentManager();
                        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.containerFrame,new TherapyFragment()).commit();
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
