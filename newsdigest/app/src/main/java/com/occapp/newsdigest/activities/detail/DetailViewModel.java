package com.occapp.newsdigest.activities.detail;

import android.text.TextUtils;

import androidx.lifecycle.ViewModel;

import com.occapp.newsdigest.network.model.NewsArticles;

public class DetailViewModel extends ViewModel {
    private NewsArticles articlesLiveData;

    public DetailViewModel() {

    }

    public NewsArticles getSelectedArticlesInfo() {
        return this.articlesLiveData;
    }


    public void init(NewsArticles selectedNewsArticle) {
        articlesLiveData = selectedNewsArticle;
    }

    public boolean isSharedObjectNull() {
        return articlesLiveData == null;
    }

    public boolean isStringEmpty(String value) {
        return TextUtils.isEmpty(value);
    }
}
