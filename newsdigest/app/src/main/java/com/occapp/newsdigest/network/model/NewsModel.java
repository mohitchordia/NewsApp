package com.occapp.newsdigest.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsModel {

    @SerializedName("status")
    private String status;

    @SerializedName("totalResults")
    private int totalResults;

    @SerializedName("articles")
    private List<NewsArticles> articlesList;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<NewsArticles> getArticlesList() {
        return articlesList;
    }

    public void setArticlesList(List<NewsArticles> articlesList) {
        this.articlesList = articlesList;
    }
}
