package com.elektra.cfct.ui.main;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elektra.cfct.R;
import com.elektra.cfct.util.VerticalSpacingItemDecoration;
import com.elektra.cfct.viewmodels.ViewModelProviderFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class ChildrenFragment extends DaggerFragment {

    private final String TAG = "ChildrenFragment";

    private ChildrenViewModel viewModel;

    private String sponsorId = "";

    private RecyclerView recyclerView;

    private FloatingActionButton fab;

    @Inject
    ChildrenRecyclerAdapter adapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_children, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String sponsorName = ChildrenFragmentArgs.fromBundle(Objects.requireNonNull(getArguments())).getSponsor_name();
        sponsorId = ChildrenFragmentArgs.fromBundle(getArguments()).getSponsor_id();

        ((MainActivity) Objects.requireNonNull(getActivity()))
                .setActionBarTitle(sponsorName);

        viewModel = new ViewModelProvider(this, providerFactory).get(ChildrenViewModel.class);

        viewModel.setSponsorId(sponsorId);

        recyclerView = view.findViewById(R.id.children_view);

        fab = view.findViewById(R.id.add_child);

        initFab();
        initRecyclerView();
        subscribeObservers();
    }

    private void initFab(){
        fab.setOnClickListener(v -> {
            addChild();
        });
    }

    private void initRecyclerView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        VerticalSpacingItemDecoration itemDecoration = new VerticalSpacingItemDecoration(15);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);
        adapter.setItemClickCallback(child -> {
            //String sponsorName =  String.format("%s %s", sponsor.getFirstName(), sponsor.getLastName());

            //findNavController(this).navigate(SponsorsFragmentDirections.action_choose_storage_location(sponsorName, sponsor.getId()));
        });
    }

    private void subscribeObservers() {
        viewModel.observeChildren().removeObservers(getViewLifecycleOwner());
        viewModel.observeChildren().observe(getViewLifecycleOwner(), listResource -> {
            if(listResource != null){
                switch (listResource.status){

                    case LOADING:{
                        Log.d(TAG, "onChanged: LOADING...");
                        break;
                    }

                    case SUCCESS:{
                        Log.d(TAG, "onChanged: got posts...");
                        adapter.setChildren(listResource.data);
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

    private void addChild(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Objects.requireNonNull(this.getActivity()));
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_child, null);
        dialogBuilder.setView(dialogView);

        final TextInputEditText firstNameTxt =  dialogView.findViewById(R.id.firstName);
        final TextInputEditText lastNameTxt =  dialogView.findViewById(R.id.lastname);

        dialogBuilder.setTitle("Add Child");
        dialogBuilder.setMessage("Please add the child details");

        dialogBuilder.setPositiveButton("Add", (dialog, which) -> {
            String fistName = firstNameTxt.getText().toString().trim();
            String lastName = lastNameTxt.getText().toString().trim();
            if(fistName.length() > 0 && lastName.length() > 0){
                viewModel.addChild(fistName,lastName);
            }
        });
        dialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }
}
