package com.example.simonepirozzi.therapyreminder.ui.home;

import com.example.simonepirozzi.therapyreminder.data.db.model.Account;
import com.example.simonepirozzi.therapyreminder.data.db.model.Task;

public class HomeContract {

    interface Presenter {
        boolean existTaskList();

        void removeOldTasks();

        void checkTodayTasks();

    }

    interface View{
        void createLayout(Task task);
    }
}
