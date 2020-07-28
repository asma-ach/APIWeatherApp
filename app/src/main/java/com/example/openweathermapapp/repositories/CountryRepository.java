package com.example.openweathermapapp.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.openweathermapapp.domain.datamodel.CountryModel;
import com.example.openweathermapapp.domain.local.AppRoomDB;
import com.example.openweathermapapp.domain.local.AppRoomDao;

import java.util.List;

public class CountryRepository {
    private AppRoomDao mDao;
    private LiveData<List<CountryModel>> mAllCountries;

    public CountryRepository(Application application) {
        AppRoomDB db = AppRoomDB.getDatabase(application);
        mDao = db.wordDao();
        mAllCountries = mDao.getAllCountries();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<CountryModel>> getAllCountries() {
        return mAllCountries;
    }


    public void insert (CountryModel countryModel) {
        new insertAsyncTask(mDao).execute(countryModel);
    }

    private static class insertAsyncTask extends AsyncTask<CountryModel, Void, Void> {

        private AppRoomDao mAsyncTaskDao;

        insertAsyncTask(AppRoomDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CountryModel... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
