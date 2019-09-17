package com.elektra.cfct.di.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.elektra.cfct.db.CfctDatabase;
import com.elektra.cfct.db.dao.ChildDao;
import com.elektra.cfct.db.dao.SponsorDao;
import com.elektra.cfct.db.entities.Sponsor;
import com.elektra.cfct.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CfctDatabaseModule {

    @Singleton
    @Provides
    CfctDatabase  provideCftcDatabase(final Context context)
    {
        return Room.databaseBuilder(
                context,
                CfctDatabase.class, Constants.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Singleton
    @Provides
    SponsorDao provideSponsorDao(CfctDatabase database) {
        return database.getSponsorDao();
    }

    @Singleton
    @Provides
    ChildDao provideChildDao(CfctDatabase database) {
        return database.getChildDao();
    }
}
