package com.example.login_page;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = User.class, version = 2)
public abstract class ApplicationDataBase extends RoomDatabase {
    public abstract userDao userDao();

    private static ApplicationDataBase instance;

    public static synchronized ApplicationDataBase getInstance(Context context) {

        if(instance == null){
            instance = Room.databaseBuilder(context, ApplicationDataBase.class, "register2_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(callback)
                    .build();

        }
        return instance;
    }

    public static RoomDatabase.Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new WorkerThread(instance).execute();

        }
    };

    public static class WorkerThread extends AsyncTask<Void, Void, Void>{

        private userDao dao;

        public WorkerThread(ApplicationDataBase db){
            this.dao = db.userDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            dao.insertSingleUser(new User("test", "hello", "test@gmail.com", "test", 939339393, "charan", true));

            return null;
        }
    }

}
