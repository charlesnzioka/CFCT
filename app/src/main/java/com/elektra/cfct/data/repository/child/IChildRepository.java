package com.elektra.cfct.data.repository.child;

import com.elektra.cfct.db.entities.Child;

import java.util.List;

import io.reactivex.Flowable;

public interface IChildRepository {
    List<Long> addChild(Child child);
    int deleteChild(Child child);
    void updateChild(Child child);
    Flowable<List<Child>> getChildrenBySponsor(String sponserId);
}
