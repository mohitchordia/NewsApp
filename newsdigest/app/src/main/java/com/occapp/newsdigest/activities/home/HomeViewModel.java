package com.occapp.newsdigest.activities.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.occapp.newsdigest.components.DaggerComponentsOur;
import com.occapp.newsdigest.network.model.NewsArticles;

import java.util.List;

import javax.inject.Inject;

public class HomeViewModel extends ViewModel {

    private LiveData<List<NewsArticles>> newLiveData;
    private HomeModel articlesRepo;

    @Inject
    public HomeViewModel() {
        articlesRepo = DaggerComponentsOur.create().getHomeModel();
    }


    public void init() {
        if (this.newLiveData == null) {
            newLiveData = new MutableLiveData<>();
        }
        newLiveData = articlesRepo.getAllArticles();
    }

    public LiveData<List<NewsArticles>> getArticlesList() {
        return this.newLiveData;
    }

    public void doNetworkCall() {
        articlesRepo.refreshNewsList();
    }

    public boolean isResponseNotNull(List<NewsArticles> list) {
        return list != null;
    }
}
