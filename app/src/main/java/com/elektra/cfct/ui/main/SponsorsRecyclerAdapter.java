package com.elektra.cfct.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elektra.cfct.R;
import com.elektra.cfct.db.entities.Sponsor;

import java.util.ArrayList;
import java.util.List;

public class SponsorsRecyclerAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Sponsor> sponsors = new ArrayList<>();
    private ItemClickCallBack mItemClickCallback;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sponsor_list_item, parent, false);
        SponsorViewHolder holder = new SponsorViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((SponsorViewHolder)holder).bind(sponsors.get(position));
    }

    @Override
    public int getItemCount() {
        return sponsors.size();
    }

    public void setSponsors(List<Sponsor> sponsors){
        this.sponsors = sponsors;
        notifyDataSetChanged();
    }

    public void setItemClickCallback(ItemClickCallBack callback){
        this.mItemClickCallback = callback;
    }

    public interface ItemClickCallBack{
        void onSponsorClicked(Sponsor sponsor);
    }

    public class SponsorViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView description;

        public SponsorViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.children_sponsored);

        }

        public void bind(Sponsor sponsor){
            title.setText(String.format("%s %s", sponsor.getFirstName(), sponsor.getLastName()));
            description.setVisibility(View.GONE);
            itemView.setOnClickListener(v -> {
                mItemClickCallback.onSponsorClicked(sponsor);
            });
        }
    }
}
