package com.example.simonepirozzi.therapyreminder;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.simonepirozzi.therapyreminder.data.db.TinyDB;
import com.example.simonepirozzi.therapyreminder.data.db.model.Doctor;

public class MedicoFragment extends Fragment {

    Button aggiungi;
    LinearLayout linearLayout;
    TinyDB db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_medico,container,false);

        aggiungi=view.findViewById(R.id.aggiungiM);
        linearLayout=view.findViewById(R.id.linearMedico);
        db=new TinyDB(view.getContext());

        if(db.getListObject("keyListaMedico", Doctor.class)!=null){
            for(int i = 0; i<db.getListObject("keyListaMedico", Doctor.class).size(); i++){
                Doctor med= (Doctor) db.getListObject("keyListaMedico", Doctor.class).get(i);


                LinearLayout ll=new LinearLayout(view.getContext());
                int dim = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
                int dim1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());

                LinearLayout.LayoutParams lparams =
                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                LinearLayout.LayoutParams paramsText =
                        new LinearLayout.LayoutParams(dim, LinearLayout.LayoutParams.WRAP_CONTENT);

                LinearLayout.LayoutParams paramsText2 =
                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                LinearLayout.LayoutParams paramsText3 =
                        new LinearLayout.LayoutParams(dim1, LinearLayout.LayoutParams.WRAP_CONTENT);
                ll.setLayoutParams(lparams);
                ll.setOrientation(LinearLayout.HORIZONTAL);

                ImageView imageView=new ImageView(view.getContext());
                imageView.setPadding(0,0,100,0);
                imageView.setImageDrawable(ContextCompat.getDrawable(view.getContext(),android.R.drawable.ic_menu_myplaces));

                TextView textView=new TextView(view.getContext());
                textView.setLayoutParams(paramsText);
                textView.setText(med.getName()+" "+med.getSurname()+"\n"+med.getNumber());
                textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                textView.setTextSize(20);



                ImageButton button=new ImageButton(view.getContext());
                button.setLayoutParams(paramsText2);
                button.setBackground(ContextCompat.getDrawable(view.getContext(),R.drawable.border2));
                button.setTag(i+"");
                button.setImageDrawable(ContextCompat.getDrawable(view.getContext(),android.R.drawable.ic_menu_edit));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.putInt("indiceMedico",Integer.parseInt(v.getTag().toString()));
                        FragmentManager fragmentManager=getFragmentManager();
                        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.containerFrame,new ModificaMedicoFragment()).commit();
                        fragmentTransaction.addToBackStack(null);
                    }
                });

                ll.addView(imageView);
                ll.addView(textView);
                ll.addView(button);

                ll.setBackground(ContextCompat.getDrawable(view.getContext(),R.drawable.border1));
                linearLayout.addView(ll);


            }
        }

        aggiungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager=getFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerFrame,new AggiungiMedicoFragment()).commit();
                fragmentTransaction.addToBackStack(null);
            }
        });

        return view;
    }
}
