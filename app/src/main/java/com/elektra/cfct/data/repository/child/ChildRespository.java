package com.elektra.cfct.data.repository.child;

import androidx.annotation.NonNull;

import com.elektra.cfct.db.CfctDatabase;
import com.elektra.cfct.db.entities.Child;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class ChildRespository implements IChildRepository {

    private final CfctDatabase database;

    @Inject
    public ChildRespository(CfctDatabase database) {
        this.database = database;
    }

    @Override
    public List<Long> addChild(Child child) {
        return this.database.getChildDao().insert(child);
    }

    @Override
    public int deleteChild(Child child) {
        return this.database.getChildDao().remove(child);
    }

    @Override
    public void updateChild(Child child) {
        this.database.getChildDao().updateChildren(child);
    }

    @Override
    public Flowable<List<Child>> getChildrenBySponsor(@NonNull String sponsorId) {
        return this.database.getChildDao().findChildSponsoredBy(sponsorId);
    }
}
