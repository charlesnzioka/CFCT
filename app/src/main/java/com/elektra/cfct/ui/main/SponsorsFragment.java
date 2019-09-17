package com.elektra.cfct.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.elektra.cfct.R;
import com.elektra.cfct.data.Resource;
import com.elektra.cfct.db.entities.Sponsor;
import com.elektra.cfct.util.VerticalSpacingItemDecoration;
import com.elektra.cfct.viewmodels.ViewModelProviderFactory;

import java.util.List;

import androidx.navigation.fragment.NavHostFragment.*;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

/**
 * A placeholder fragment containing a simple view.
 */
public class SponsorsFragment extends DaggerFragment {

    private static final String TAG = "SponsorsFragment";

    private SponsorsViewModel viewModel;
    private RecyclerView recyclerView;

    @Inject
    SponsorsRecyclerAdapter adapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    public SponsorsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.sponsors_view);

        viewModel = new ViewModelProvider(this, providerFactory).get(SponsorsViewModel.class);

        initRecyclerView();
        subscribeObservers();
    }

    private void subscribeObservers(){
        viewModel.observeSponsors().removeObservers(getViewLifecycleOwner());
        viewModel.observeSponsors().observe(getViewLifecycleOwner(), listResource -> {
            if(listResource != null){
                switch (listResource.status){

                    case LOADING:{
                        Log.d(TAG, "onChanged: LOADING...");
                        break;
                    }

                    case SUCCESS:{
                        Log.d(TAG, "onChanged: got posts...");
                        adapter.setSponsors(listResource.data);
                        break;
                    }

                    case ERROR:{
                        Log.e(TAG, "onChanged: ERROR..." + listResource.message );
                        break;
                    }
                }
            }
        });
    }

    private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        VerticalSpacingItemDecoration itemDecoration = new VerticalSpacingItemDecoration(15);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);
        adapter.setItemClickCallback(sponsor -> {
            String sponsorName =  String.format("%s %s", sponsor.getFirstName(), sponsor.getLastName());

            findNavController(this).navigate(SponsorsFragmentDirections.action_choose_storage_location(sponsorName, sponsor.getId()));
        });
    }

}
