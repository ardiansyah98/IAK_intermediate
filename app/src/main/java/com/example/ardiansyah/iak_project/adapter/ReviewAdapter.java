package com.example.ardiansyah.iak_project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ardiansyah.iak_project.R;
import com.example.ardiansyah.iak_project.model.ReviewModel;

import java.util.List;

/**
 * Created by Ardiansyah on 13/08/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.AdapterHolder>{

    public Context mContext;
    public List<ReviewModel.Result> reviewList;

    public ReviewAdapter (Context mContext, List<ReviewModel.Result> reviewList){
        this.mContext = mContext;
        this.reviewList = reviewList;
    }

    public class AdapterHolder extends RecyclerView.ViewHolder {
        TextView txtAuthor, txtContent;

        public AdapterHolder(View itemView) {
            super(itemView);
            txtAuthor = (TextView)itemView.findViewById(R.id.txtAuthor);
            txtContent = (TextView)itemView.findViewById(R.id.txtContent);
        }
    }

    @Override
    public ReviewAdapter.AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(mContext).inflate(R.layout.list_review_item, parent, false);
        ReviewAdapter.AdapterHolder adapterHolder = new ReviewAdapter.AdapterHolder(rowView);
        return adapterHolder;
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.AdapterHolder holder, int position) {
        holder.txtAuthor.setText(reviewList.get(position).author);
        holder.txtContent.setText(reviewList.get(position).content);
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }


}
