package com.example.simonepirozzi.therapyreminder;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
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
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Intent;

import com.example.simonepirozzi.therapyreminder.data.db.TinyDB;
import com.example.simonepirozzi.therapyreminder.data.db.model.Task;
import com.example.simonepirozzi.therapyreminder.service.AlarmReceiver;
import com.example.simonepirozzi.therapyreminder.ui.therapy.TherapyFragment;

import java.util.Calendar;
import java.util.Date;

import static java.lang.Integer.parseInt;

public class AggiungiTerapieFragment extends Fragment {
    Button aggiungi;
    EditText attivita,numberDurata,orario,note;
    CheckBox lun,mar,mer,gio,ven,sab,dom;
    TinyDB db;
        ArrayList<Object> attivitàArrayList;
    ArrayList<Task> taskArrayList1;

    AlarmManager alarm_Manager;
    private PendingIntent pending_intent;

    private TextView alarmTextView;

    AlarmReceiver alarm;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.add_fragment_therapy,container,false);
        aggiungi=view.findViewById(R.id.addTerapia);
        attivita=view.findViewById(R.id.attivita);
        numberDurata=view.findViewById(R.id.numberDurata);
        orario=view.findViewById(R.id.orario);
        note=view.findViewById(R.id.note);
        lun=view.findViewById(R.id.comboLunedi);
        mar=view.findViewById(R.id.comboMartedi);
        mer=view.findViewById(R.id.comboMercoledi);
        gio=view.findViewById(R.id.comboGiovedi);
        ven=view.findViewById(R.id.comboVenerdi);
        sab=view.findViewById(R.id.comboSabato);
        dom=view.findViewById(R.id.comboDomenica);

        alarm_Manager=  (AlarmManager) view.getContext().getSystemService(Context.ALARM_SERVICE);
        final Calendar calendar = Calendar.getInstance();



        db=new TinyDB(view.getContext());



        aggiungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(attivita.getText().toString().length()!=0  && numberDurata.getText().toString().length()!=0 &&
                        (lun.isChecked() || mar.isChecked() || mer.isChecked()|| gio.isChecked() || ven.isChecked() ||
                        sab.isChecked() || dom.isChecked()) && orario.getText().toString().length()!=0){

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
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = new Date();

                    Task task =new Task(attivita.getText().toString(),orario.getText().toString(),note.getText().toString(),
                            arrayList,numberDurata.getText().toString(),date);



                    if(db.getListObject("keyListaAttivita", Task.class)==null)

                        attivitàArrayList=new ArrayList<Object>();

                    else
                        attivitàArrayList= db.getListObject("keyListaAttivita", Task.class);


                    attivitàArrayList.add(task);

                    db.putListObject("keyListaAttivita",attivitàArrayList);





                    final int hour = Integer.parseInt(task.getTime().substring(0, task.getTime().indexOf(":")));
                    final int minute = Integer.parseInt(task.getTime().substring((task.getTime().indexOf(":"))+1));

                    calendar.add(Calendar.SECOND, 3);

                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);

                    for(int i = 0; i< task.getListDays().size(); i++){
                        if(task.getListDays().get(i).equalsIgnoreCase("venerdi")){
                            calendar.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);
                        }
                       else if(task.getListDays().get(i).equalsIgnoreCase("sabato")){
                            calendar.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);


                        }
                        else if(task.getListDays().get(i).equalsIgnoreCase("lunedi")){
                            calendar.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);

                        }
                        else if(task.getListDays().get(i).equalsIgnoreCase("martedi")){
                            calendar.set(Calendar.DAY_OF_WEEK,Calendar.TUESDAY);

                        }
                        else if(task.getListDays().get(i).equalsIgnoreCase("mercoledi")){
                            calendar.set(Calendar.DAY_OF_WEEK,Calendar.WEDNESDAY);

                        }
                        else if(task.getListDays().get(i).equalsIgnoreCase("giovedi")){
                            calendar.set(Calendar.DAY_OF_WEEK,Calendar.THURSDAY);

                        }
                        else if(task.getListDays().get(i).equalsIgnoreCase("domenica")){
                            calendar.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);

                        }

                    }

                    final Intent myIntent = new Intent(view.getContext(), AlarmReceiver.class);

                    myIntent.putExtra("extra", "yes");
                    myIntent.putExtra("orario", task.getTime());
                    myIntent.putExtra("nomeAtt", task.getTask()+" ");
                    if(task.getNote().length()>0)
                        myIntent.putExtra("dose","Dosaggio:"+ task.getNote());

                    pending_intent = PendingIntent.getBroadcast(view.getContext(), 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    alarm_Manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pending_intent);



























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


        return view;
    }
}
