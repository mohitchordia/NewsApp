package com.occapp.newsdigest.activities.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.occapp.newsdigest.R;
import com.occapp.newsdigest.Utils;
import com.occapp.newsdigest.network.model.NewsArticles;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    private static final String SELECTED_ARTICLES_INFO = "SELECTED_ARTICLES_INFO";

    @BindView(R.id.articlesImage)
    ImageView mImageView;
    @BindView(R.id.source)
    TextView source;
    @BindView(R.id.publishedAt)
    TextView publishedAt;
    @BindView(R.id.information)
    TextView information;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.title)
    TextView title;

    private DetailViewModel viewModel;

    public static void initiateDetailActivity(Context context, NewsArticles selectedObject, View view) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(SELECTED_ARTICLES_INFO, selectedObject);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        viewModel.init(getIntent().getParcelableExtra(SELECTED_ARTICLES_INFO));

        if (viewModel.isSharedObjectNull()) {
            finish();
            return;
        }
        setupToolbar();
        setupOnUI();
    }


    private void setupToolbar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(view -> onBackPressed());
    }


    private void setupOnUI() {
        NewsArticles articles = viewModel.getSelectedArticlesInfo();
        Utils.loadImage(this, mImageView, articles.getUrlToImage(), R.drawable.ic_place_holder, R.drawable.ic_broken_image);
        title.setText(articles.getTitle().trim());

        if (!viewModel.isStringEmpty(articles.getSource().getName())) {
            source.setText(articles.getSource().getName().trim());
        }

        if (!viewModel.isStringEmpty(articles.getPublishedAt())) {
            publishedAt.setText(Utils.getDateFormat(articles.getPublishedAt()));
        }

        if (!viewModel.isStringEmpty(articles.getContent()))
            information.setText(articles.getContent());
    }

}
