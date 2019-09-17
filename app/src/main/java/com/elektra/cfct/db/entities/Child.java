package com.elektra.cfct.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "child", foreignKeys = @ForeignKey(entity = Sponsor.class,
                                                                parentColumns = "id",
                                                                childColumns = "sponsor_id"))
public class Child {
    @PrimaryKey
    @NonNull
    private String id = UUID.randomUUID().toString();
    @ColumnInfo(name = "first_name") private  String firstName;
    @ColumnInfo(name = "last_name") private String lastName;
    private int age;
    private String gender;
    @ColumnInfo(name = "sponsor_id", index = true) private String sponsorId;

    public Child(@NonNull String firstName, @NonNull String lastName, int age, @NonNull String gender, String sponsorId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.sponsorId = sponsorId;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(String sponsorId) {
        this.sponsorId = sponsorId;
    }
}
