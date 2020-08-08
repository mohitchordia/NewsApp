package com.occapp.newsdigest.activities.home;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.occapp.newsdigest.R;
import com.occapp.newsdigest.SimpleIdlingResource;
import com.occapp.newsdigest.Utils;
import com.occapp.newsdigest.activities.detail.DetailActivity;
import com.occapp.newsdigest.adapters.NewsHeadlinesAdapter;
import com.occapp.newsdigest.network.interceptors.ErrorCode;
import com.occapp.newsdigest.network.model.NewsArticles;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements NewsHeadlinesAdapter.ListItemOnClickListener {

    @Nullable
    public static SimpleIdlingResource mIdlingResource;
    HomeViewModel viewModel;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    NewsHeadlinesAdapter adapter;
    List<NewsArticles> list;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    RecyclerView.LayoutManager layoutManager;
    private String TAG = HomeActivity.class.getName();

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public static IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            synchronized (HomeActivity.class) {
                if (mIdlingResource == null) {
                    mIdlingResource = new SimpleIdlingResource();
                }
            }
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        getIdlingResource();
        mIdlingResource.setIdleState(false);
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        Utils.showViews(progressBar);
        viewModel.init();
        setupToolbar();
        bindAdapter();
        setupObserver();
    }

    private void setupToolbar() {
        mToolbar.setTitle(R.string.headlines);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void setupObserver() {
        viewModel.getArticlesList().observe(this, newsResponse -> {
            if (viewModel.isResponseNotNull(newsResponse)) {
                adapter.addAllItem(newsResponse);
                mIdlingResource.setIdleState(true);
            }
            Utils.hideViews(progressBar);
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Integer event) {
        Utils.hideViews(progressBar);
        switch (event) {
            case ErrorCode.INTERNET_ERROR:
                Toast.makeText(this, getString(R.string.no_connectivity_exception_msg), Toast.LENGTH_LONG).show();
                break;
            default:
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void bindAdapter() {
        adapter = new NewsHeadlinesAdapter(list, this) {
            @Override
            public void loadMore() {
                Utils.showViews(progressBar);
                viewModel.doNetworkCall();
            }
        };

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onListItemClick(NewsArticles selectedObject, View view) {
        Log.d(TAG, "clicked :" + selectedObject.getTitle());
        DetailActivity.initiateDetailActivity(this,selectedObject,view);
    }
}
