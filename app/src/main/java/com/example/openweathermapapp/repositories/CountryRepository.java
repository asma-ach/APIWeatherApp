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

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
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

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
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
