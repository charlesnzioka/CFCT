package com.elektra.cfct.data.repository.sponsor;

import com.elektra.cfct.db.entities.Sponsor;

import java.util.List;

import io.reactivex.Flowable;

public interface ISponsorRepository {

    Flowable<List<Sponsor>> loadSponsors();
}
