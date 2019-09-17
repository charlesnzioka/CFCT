package com.elektra.cfct.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.elektra.cfct.db.dao.ChildDao;
import com.elektra.cfct.db.dao.SponsorDao;
import com.elektra.cfct.db.entities.Child;
import com.elektra.cfct.db.entities.Sponsor;

@Database(entities = {Sponsor.class, Child.class}, version = 1, exportSchema = false)
public abstract class CfctDatabase extends RoomDatabase {

    public abstract SponsorDao getSponsorDao();
    public abstract ChildDao getChildDao();

}
