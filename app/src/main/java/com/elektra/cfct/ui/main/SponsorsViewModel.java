package com.elektra.cfct.ui.main;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.elektra.cfct.data.Resource;
import com.elektra.cfct.data.repository.sponsor.SponsorRepository;
import com.elektra.cfct.db.entities.Sponsor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;

public class SponsorsViewModel extends ViewModel {

    private static final String TAG = "SponsorsViewModel";

    // inject
    private final SponsorRepository sponsorRepository;

    private MediatorLiveData<Resource<List<Sponsor>>> sponsors;

    @Inject
    public SponsorsViewModel(SponsorRepository sponsorRepository) {
        this.sponsorRepository = sponsorRepository;
        Log.d(TAG, "SponsorsViewModel: viewmodel is working...");
    }

    public LiveData<Resource<List<Sponsor>>> observeSponsors(){
        if(sponsors == null){
            sponsors = new MediatorLiveData<>();
            sponsors.setValue(Resource.loading(null));

            final LiveData<Resource<?>> source = LiveDataReactiveStreams.fromPublisher(

                    sponsorRepository.loadSponsors()

                            .onErrorReturn(throwable -> {
                                Log.e(TAG, "apply: ", throwable);
                                Sponsor sponsor = new Sponsor("","","","");
                                sponsor.setId("-1");
                                ArrayList<Sponsor> sponsors = new ArrayList<>();
                                sponsors.add(sponsor);
                                return sponsors;
                            })

                            .map(sponsors -> {

                                if(sponsors.size() > 0){
                                    if(sponsors.get(0).getId() == "-1"){
                                        return Resource.error("Something went wrong", null);
                                    }
                                }
                                else{
                                    sponsorRepository.seedSponsors();
                                }

                                return Resource.success(sponsors);
                            })

                            .subscribeOn(Schedulers.io())
            );

            sponsors.addSource(source, listResource -> {
                sponsors.setValue((Resource<List<Sponsor>>) listResource);
                sponsors.removeSource(source);
            });
        }
        return sponsors;
    }
}
