package com.example.simonepirozzi.therapyreminder.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.simonepirozzi.therapyreminder.R;
import com.example.simonepirozzi.therapyreminder.data.db.TinyDB;
import com.example.simonepirozzi.therapyreminder.data.db.model.Task;
import com.example.simonepirozzi.therapyreminder.utils.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment implements HomeContract.View {
    LinearLayout linearLayout;
    TinyDB db;
    private HomePresenter homePresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        linearLayout = view.findViewById(R.id.linearMemo);
        db = new TinyDB(view.getContext());
        homePresenter = new HomePresenter(requireContext(), db, this);

        if (homePresenter.existTaskList()) {
            homePresenter.removeOldTasks();
            homePresenter.checkTodayTasks();
        }
    }

    @Override
    public void createLayout(Task att) {
        LinearLayout ll = new LinearLayout(requireContext());
        int dim = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());

        LinearLayout.LayoutParams lparams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams paramsText =
                new LinearLayout.LayoutParams(dim, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams paramsText1 =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.setLayoutParams(lparams);
        ll.setOrientation(LinearLayout.HORIZONTAL);

        TextView textView = new TextView(requireContext());
        TextView textView1 = new TextView(requireContext());
        textView.setLayoutParams(paramsText);
        textView.setText(att.getTime());
        textView.setTextColor(getResources().getColor(R.color.colorPrimary));
        textView.setTextSize(20);

        textView1.setLayoutParams(paramsText1);
        if (att.getNote().length() == 0)
            textView1.setText(att.getTask() + "\n" + getString(R.string.home_duration) + att.getTask() + getString(R.string.home_days));
        else
            textView1.setText(att.getTask() + "\n" + getString(R.string.home_quantity) + att.getNote()
                    + getString(R.string.home_duration) + att.getDuration() + getString(R.string.home_days));

        textView1.setTextColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_focused));
        textView1.setTextSize(20);


        ll.addView(textView);
        ll.addView(textView1);

        ll.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.border1));
        linearLayout.addView(ll);
    }
}
