package com.example.vipul.sports_arena;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vipul on 4/12/17.
 */

public class ArenaReviewAdapter extends RecyclerView.Adapter<ArenaReviewAdapter.ReviewsViewHolder> {

    private Context mContext;
    private List<ArenaReview> reviewList;

    public ArenaReviewAdapter(Context mContext, List<ArenaReview> reviewList) {
        this.mContext = mContext;
        this.reviewList = reviewList;
    }


    @Override
    public ReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.review_card,null);
        view.setClickable(false);
        return new ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewsViewHolder holder, int position) {

        final ArenaReview review = reviewList.get(position);
        holder.review.setText(review.getReviewText());
        holder.reviewer.setText(review.getReviewBy());
        holder.ratingBar.setRating(Float.valueOf(review.getReviewRating()));

    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    class ReviewsViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;
        TextView review,reviewer;
        RatingBar ratingBar;

        public ReviewsViewHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.reviewLinear);
            review=itemView.findViewById(R.id.reviewText);
            reviewer=itemView.findViewById(R.id.ratingBy);
            ratingBar=itemView.findViewById(R.id.reviewRating);
        }
    }
}
