package com.elektra.cfct.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.elektra.cfct.db.entities.Child;
import com.elektra.cfct.db.entities.Sponsor;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface ChildDao {

    @Query("SELECT * FROM child")
    Flowable<List<Child>> allChildren();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(Child... children);

    @Delete
    int remove (Child... child);

    @Query("SELECT * FROM child where first_name  like :firstName")
    Flowable<List<Child>> findChildByFirstName(String firstName);

    @Query("SELECT * FROM child where last_name  like :lastName")
    Flowable<List<Child>> findChildByLastName(String lastName);

    @Query("SELECT * FROM child WHERE first_name LIKE :search " +
            "OR last_name LIKE :search")
    Flowable<List<Child>> findChildWithName(String search);

    @Update
    void updateChildren(Child... children);

    @Query("SELECT * FROM child WHERE sponsor_id LIKE :search ")
    Flowable<List<Child>> findChildSponsoredBy(String search);
}
