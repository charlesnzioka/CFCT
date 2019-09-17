package com.elektra.cfct.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elektra.cfct.R;
import com.elektra.cfct.db.entities.Child;
import com.elektra.cfct.db.entities.Sponsor;

import java.util.ArrayList;
import java.util.List;

public class ChildrenRecyclerAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Child> children = new ArrayList<>();
    private ChildrenRecyclerAdapter.ItemClickCallBack mItemClickCallback;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sponsor_list_item, parent, false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ChildrenRecyclerAdapter.ChildViewHolder)holder).bind(children.get(position));
    }

    @Override
    public int getItemCount() {
        return children.size();
    }

    public void setChildren(List<Child> children){
        this.children = children;
        notifyDataSetChanged();
    }

    public void setItemClickCallback(ChildrenRecyclerAdapter.ItemClickCallBack callback){
        this.mItemClickCallback = callback;
    }

    public interface ItemClickCallBack{
        void onSponsorClicked(Child child);
    }

    public class ChildViewHolder extends RecyclerView.ViewHolder{

        TextView title;

        public ChildViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);

        }



        public void bind(Child child){
            title.setText(String.format("%s %s", child.getFirstName(), child.getLastName()));
            itemView.setOnClickListener(v -> mItemClickCallback.onSponsorClicked(child));
        }
    }
}
