package com.elektra.cfct.ui.main;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.elektra.cfct.data.Resource;
import com.elektra.cfct.data.repository.child.ChildRespository;
import com.elektra.cfct.data.repository.sponsor.SponsorRepository;
import com.elektra.cfct.db.entities.Child;
import com.elektra.cfct.db.entities.Sponsor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;

public class ChildrenViewModel extends ViewModel {

    private static final String TAG = "ChildrenViewModel";

    public String getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(String sponsorId) {
        this.sponsorId = sponsorId;
    }

    private String sponsorId;

    // inject
    private final ChildRespository childRepository;

    @Inject
    public ChildrenViewModel(ChildRespository childRepository) {
        this.childRepository = childRepository;
        Log.d(TAG, "ChildrenViewModel: viewmodel is working...");
    }

    private MediatorLiveData<Resource<List<Child>>> children;

    public void addChild(String fName, String lName){
        Child child = new Child(fName,lName,9,"Male", sponsorId);
        childRepository.addChild(child);
        children.getValue().data.add(child);
    }

    public LiveData<Resource<List<Child>>> observeChildren(){
        if(children == null){
            children = new MediatorLiveData<>();
            children.setValue(Resource.loading(null));

            final LiveData<Resource<?>> source = LiveDataReactiveStreams.fromPublisher(

                    childRepository.getChildrenBySponsor(this.sponsorId)

                            .onErrorReturn(throwable -> {
                                Log.e(TAG, "apply: ", throwable);
                                Child child = new Child("","",0,"","");
                                child.setId("-1");
                                ArrayList<Child> children = new ArrayList<>();
                                children.add(child);
                                return children;
                            })

                            .map(children -> {

                                if(children.size() > 0){
                                    if(children.get(0).getId() == "-1"){
                                        return Resource.error("Something went wrong", null);
                                    }
                                }

                                return Resource.success(children);
                            })

                            .subscribeOn(Schedulers.io())
            );

            children.addSource(source, listResource -> {
                children.setValue((Resource<List<Child>>) listResource);
                children.removeSource(source);
            });
        }
        return children;
    }
}
