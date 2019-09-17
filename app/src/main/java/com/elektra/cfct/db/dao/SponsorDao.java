package com.elektra.cfct.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.elektra.cfct.db.entities.Sponsor;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface SponsorDao {

    @Query("SELECT * FROM sponsor")
    Flowable<List<Sponsor>> allSponsors();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(List<Sponsor> sponsors);

    @Delete
    int remove (Sponsor... sponsors);

    @Query("SELECT * FROM sponsor where id  like :identification")
    Single<Sponsor> findSponsorById(String identification);
}
