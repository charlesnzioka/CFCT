package com.elektra.cfct.data.repository.sponsor;
import com.elektra.cfct.db.CfctDatabase;
import com.elektra.cfct.db.entities.Sponsor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;


@Singleton
public class SponsorRepository implements  ISponsorRepository{

    private static final String TAG = "SponsorRepository";

    private final CfctDatabase database;

    @Inject
    public SponsorRepository(CfctDatabase database) {
        this.database = database;
    }

    @Override
    public Flowable<List<Sponsor>> loadSponsors() {
        return  database.getSponsorDao().allSponsors();
    }

    public void seedSponsors(){
        List<Sponsor> sponsors = new ArrayList<>();
        sponsors.add(new Sponsor("John", "Doe", "Atalanta", "Male"));
        sponsors.add(new Sponsor("Donald", "Trump", "Washington DC", "Male"));
        sponsors.add(new Sponsor("Barrack", "Obama", "Hawaii", "Male"));
        sponsors.add(new Sponsor("Hilary", "Clinton", "Alabama", "Female"));

        database.getSponsorDao().insert(sponsors);
    }
}
