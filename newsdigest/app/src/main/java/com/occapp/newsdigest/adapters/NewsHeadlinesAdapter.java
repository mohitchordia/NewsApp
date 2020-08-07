package com.occapp.newsdigest.adapters;


import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.occapp.newsdigest.R;
import com.occapp.newsdigest.Utils;
import com.occapp.newsdigest.network.model.NewsArticles;

import java.util.ArrayList;
import java.util.List;


/**
 * This is an adapter which keeps the list of new's articles information;
 * 1. have n number of pages so based on pages getting new list of new's articles information.
 * 2. adding every time new list of information into existing list using addAllItem method.
 * 3. setting into viewHolder
 * 4. add click listener
 */

public abstract class NewsHeadlinesAdapter extends RecyclerView.Adapter<NewsHeadlinesAdapter.MyViewHolder> {
    private final ListItemOnClickListener mOnClickListener;
    private List<NewsArticles> list;

    public NewsHeadlinesAdapter(List<NewsArticles> list, ListItemOnClickListener mOnClickListener) {
        this.list = list;
        this.mOnClickListener = mOnClickListener;
    }

    /***
     * this method used to add list into existing list
     * @param mList contains latest list which is fetched based on page wise;
     */
    public void addAllItem(final List<NewsArticles> mList) {
        if (list == null) {
            list = new ArrayList<>();
        }
        list.addAll(mList);
        this.notifyDataSetChanged();
    }

    public void clearAllItems() {
        if (list != null && list.size() > 0) {
            list.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.headline_item_list_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.bindView(position);
        if (position >= getItemCount() - 1) {
            loadMore();
        }
    }

    public abstract void loadMore();


    @Override
    public int getItemCount() {
        if (list == null || list.size() == 0)
            return 0;
        return list.size();
    }

    /***
     * listener for viewHolder's items
     */
    public interface ListItemOnClickListener {
        void onListItemClick(NewsArticles selectedObject, View view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView headlineImage;
        TextView headlineTitle, source, articlePublishedAt;

        MyViewHolder(View view) {
            super(view);
            init(view);
        }

        private void init(View view) {
            headlineTitle = view.findViewById(R.id.articlesHeadline);
            headlineImage = view.findViewById(R.id.articlesImage);
            source = view.findViewById(R.id.source);
            articlePublishedAt = view.findViewById(R.id.articlePublishedAt);
            itemView.setOnClickListener(this);
        }

        private void bindView(int position) {
            Utils.loadImage(itemView.getContext(), headlineImage, list.get(position).getUrlToImage(), R.drawable.ic_place_holder, R.drawable.ic_place_holder);
            headlineTitle.setText(list.get(position).getTitle());

            setupInformation(list.get(position).getSource().getName(), source, false);
            setupInformation(list.get(position).getPublishedAt(), articlePublishedAt, true);
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClick(list.get(getAdapterPosition()), v.findViewById(R.id.articlesImage));
        }

        private void setupInformation(String info, TextView source, boolean isDateView) {
            if (TextUtils.isEmpty(info)) {
                source.setVisibility(View.GONE);
                return;
            }

            source.setVisibility(View.VISIBLE);
            if (isDateView)
                source.setText(Utils.getDateFormat(info));
            else
                source.setText(info.trim());

        }
    }


}
