package com.example.simonepirozzi.therapyreminder.ui.home;

import android.content.Context;

import com.example.simonepirozzi.therapyreminder.data.db.TinyDB;
import com.example.simonepirozzi.therapyreminder.data.db.model.Account;
import com.example.simonepirozzi.therapyreminder.data.db.model.Task;
import com.example.simonepirozzi.therapyreminder.ui.login.LoginContract;
import com.example.simonepirozzi.therapyreminder.utils.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomePresenter implements HomeContract.Presenter {

    private Context context;
    private TinyDB db;
    private HomeContract.View view;

    public HomePresenter(Context context, TinyDB db, HomeContract.View view){
        this.context = context;
        this.db = db;
        this.view = view;
    }


    @Override
    public boolean existTaskList() {
        return db.getListObject(Constants.TASK_LIST_KEY, Task.class) != null
                && db.getListObject(Constants.TASK_LIST_KEY, Task.class).size()>0;
    }

    @Override
    public void removeOldTasks() {
        for (int j = 0; j < db.getListObject(Constants.TASK_LIST_KEY, Task.class).size(); j++) {
            Task ex = (Task) db.getListObject(Constants.TASK_LIST_KEY, Task.class).get(j);
            Date oggi = new Date();
            long diff = oggi.getTime() - ex.getDate().getTime();
            if (diff == Long.parseLong(ex.getDuration())) {
                ArrayList<Object> taskArrayList = db.getListObject(Constants.TASK_LIST_KEY, Task.class);
                taskArrayList.remove(j);
                db.putListObject(Constants.TASK_LIST_KEY, taskArrayList);
            }
        }
    }

    @Override
    public void checkTodayTasks() {
        boolean isInsert;
        for (int i = 0; i < db.getListObject(Constants.TASK_LIST_KEY, Task.class).size(); i++) {
            isInsert = false;
            Task att = (Task) db.getListObject(Constants.TASK_LIST_KEY, Task.class).get(i);
            ArrayList<String> days = att.getListDays();
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            if (days != null) {
                for (int j = 0; j < days.size(); j++) {
                    if (days.get(j).equalsIgnoreCase(String.valueOf(HomeDaysEnum.MONDAY)) && day == Calendar.MONDAY)
                        isInsert = true;
                    if (days.get(j).equalsIgnoreCase(String.valueOf(HomeDaysEnum.TUESDAY)) && day == Calendar.TUESDAY)
                        isInsert = true;
                    if (days.get(j).equalsIgnoreCase(String.valueOf(HomeDaysEnum.WEDNESDAY)) && day == Calendar.WEDNESDAY)
                        isInsert = true;
                    if (days.get(j).equalsIgnoreCase(String.valueOf(HomeDaysEnum.THURSDAY)) && day == Calendar.THURSDAY)
                        isInsert = true;
                    if (days.get(j).equalsIgnoreCase(String.valueOf(HomeDaysEnum.FRIDAY)) && day == Calendar.FRIDAY)
                        isInsert = true;
                    if (days.get(j).equalsIgnoreCase(String.valueOf(HomeDaysEnum.SATURDAY)) && day == Calendar.SATURDAY)
                        isInsert = true;
                    if (days.get(j).equalsIgnoreCase(String.valueOf(HomeDaysEnum.SUNDAY)) && day == Calendar.SUNDAY)
                        isInsert = true;
                }
            }

            if (isInsert) {
                view.createLayout(att);
            }


        }
    }
}
