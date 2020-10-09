package com.example.news.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.news.R;
import com.example.news.listeners.NewsListener;
import com.example.news.model.Article;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    ArrayList<Article> articles;
    Context context;
    NewsListener newsListener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView date;
        LinearLayout mainView;

        public ViewHolder(View v, Context context) {
            super(v);
            title = v.findViewById(R.id.title);
            date = v.findViewById(R.id.date);
            mainView = v.findViewById(R.id.main_view);
        }
    }


    public NewsAdapter(Context context, ArrayList<Article> articles,NewsListener newsListener) {
        this.context = context;
        this.articles = articles;
        this.newsListener = newsListener;
    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.single_news_view, parent, false);
        ViewHolder vh = new ViewHolder(v,parent.getContext());
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.title.setText(articles.get(position).getTitle());
        holder.date.setText(articles.get(position).getUrl());
        holder.mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsListener.onNewsClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

}
