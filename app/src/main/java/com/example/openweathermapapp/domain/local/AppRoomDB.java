package com.example.openweathermapapp.domain.local;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.openweathermapapp.domain.datamodel.CountryModel;

@Database(entities = {CountryModel.class}, version = 1,  exportSchema = false)
public abstract class AppRoomDB extends RoomDatabase {

    public abstract AppRoomDao wordDao();

    private static AppRoomDB INSTANCE;

    public static AppRoomDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppRoomDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppRoomDB.class, "country_db")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    /**
     * Populate the database in the background.
     * If you want to start with more words, just add them.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppRoomDao mDao;
        String [] countries = {"dolphin", "crocodile", "cobra"};

        PopulateDbAsync(AppRoomDB db) {
            mDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            //mDao.deleteAll();

            for( int i = 0; i <= countries.length - 1; i++) {
                CountryModel country = new CountryModel("test", "test");
                mDao.insert(country);
            }
            return null;
        }
    }
}

