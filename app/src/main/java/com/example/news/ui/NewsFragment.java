package com.example.news.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.news.R;
import com.example.news.adapters.NewsAdapter;
import com.example.news.listeners.NewsListener;
import com.example.news.model.Article;

import java.util.ArrayList;

public class NewsFragment extends Fragment implements NewsListener {
    ArrayList<Article> articles = new ArrayList<>();
    RecyclerView mRecyclerView;
    NewsAdapter newsAdapter;

    public NewsFragment() {
        // Required empty public constructor
    }

    public static NewsFragment newInstance(Bundle args) {
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        articles = getArguments().getParcelableArrayList("array");
        Log.e("TAG",articles.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpNewsAdapter();
    }

    private void setUpNewsAdapter(){
        mRecyclerView = getView().findViewById(R.id.recyclerView);
        newsAdapter = new NewsAdapter(getContext(),articles,this);
        mRecyclerView.setAdapter(newsAdapter);
    }

    @Override
    public void onNewsClick(int position) {
        Intent intent = new Intent(getActivity(),NewsDetailsActivity.class);
        intent.putExtra("url",articles.get(position).getUrl());
        getActivity().startActivity(intent);
    }
}