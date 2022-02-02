package com.example.simonepirozzi.therapyreminder;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.simonepirozzi.therapyreminder.data.db.TinyDB;
import com.example.simonepirozzi.therapyreminder.data.db.model.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MonitoraggioTerapieFragment extends Fragment {
    Button aggiungi,monitora;
    LinearLayout linearLayout;
    Button monitoraggio;
    int index;
    TinyDB db;
    ArrayList<Task> listaTerapie;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_terapie_mon,container,false);
        linearLayout=view.findViewById(R.id.linearTerapieMon);
        db=new TinyDB(view.getContext());

        if(db.getListObject("keyListaAttivita", Task.class)!=null){
          //  listaTerapie=  (ArrayList<Attività>)db.getListObject("keyListaAttivita",Attività.class);
            for(int i = 0; i<db.getListObject("keyListaAttivita", Task.class).size(); i++){
                index=i;
                Task att= (Task) db.getListObject("keyListaAttivita", Task.class).get(i);
                LinearLayout ll=new LinearLayout(view.getContext());
                int dim = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, getResources().getDisplayMetrics());
                int dim1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());

                LinearLayout.LayoutParams lparams =
                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams paramsText =
                        new LinearLayout.LayoutParams(dim, LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams paramsText1 =
                        new LinearLayout.LayoutParams(dim1, LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams paramsText2 =
                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                ll.setLayoutParams(lparams);
                ll.setOrientation(LinearLayout.HORIZONTAL);

                TextView textView=new TextView(view.getContext());
                TextView textView1=new TextView(view.getContext());
                textView.setLayoutParams(paramsText);
                textView.setText("Terapia "+(i+1));
                textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                textView.setTextSize(20);

                textView1.setLayoutParams(paramsText1);

                Date oggi=new Date();
                long diff= oggi.getTime()-att.getDate().getTime();
                Log.d("cazzo",diff+" ");
                long diffGiorni= TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS); // giorni completati
                long giorniRimanenti=Long.parseLong(att.getDuration())-diffGiorni;
                int val=0;
                String a=att.getNote();
                String b="";
                String c="";
                long completato=0;
                long rimane=0;
                if(a!=null && a.length()>0){
                    for (int q=0; q< a.length(); q++)
                    {
                        if (Character.isDigit(a.charAt(q)))
                            b += a.charAt(q);
                        else
                            c+=a.charAt(q);
                    }

                    if (b.length()>0){
                        val = Integer.parseInt(b);
                        completato=val*diffGiorni;
                        rimane=val*giorniRimanenti;

                    }




                }



                if(att.getNote().length()==0)
                    textView1.setText(att.getTask()+"\n"+
                            "Durata:"+att.getDuration()+"gg"+"\n"+"Giorni completati:"+diffGiorni+"\n"+
                            "Giorni Rimanenti"+giorniRimanenti+"\n");
                else
                    textView1.setText(att.getTask()+"\n"+"Dose:"+ att.getNote()+"\n"+
                            "Durata:"+att.getDuration()+"gg"+"\n"+"Giorni completati:"+diffGiorni+"\n"+
                            "Giorni Rimanenti"+giorniRimanenti+"\n"+"Dose completata: "+completato+" "+c+
                    "\n"+"Dose rimanente: "+rimane+" "+c);

                textView1.setTextColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_focused));
                textView1.setTextSize(20);



                ll.addView(textView);
                ll.addView(textView1);

                ll.setBackground(ContextCompat.getDrawable(view.getContext(),R.drawable.border1));
                linearLayout.addView(ll);


            }
        }




        return view;
    }
}
