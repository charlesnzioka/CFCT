package com.elektra.cfct.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "sponsor")
public class Sponsor {
    @PrimaryKey
    @NonNull
    private String id = UUID.randomUUID().toString();
    @ColumnInfo(name = "first_name") private String firstName;
    @ColumnInfo(name = "last_name") private String lastName;
    private String location;
    private String gender;

    public  Sponsor(@NonNull String firstName, @NonNull String lastName, String location,
                 @NonNull  String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.gender = gender;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
