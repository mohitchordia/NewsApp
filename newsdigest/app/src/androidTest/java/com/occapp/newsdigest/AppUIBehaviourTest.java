package com.occapp.newsdigest;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import com.occapp.newsdigest.activities.home.HomeActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class AppUIBehaviourTest {

    @Rule
    public ActivityTestRule<HomeActivity> mHomeActivity = new ActivityTestRule<>(HomeActivity.class);


    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mHomeActivity.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Test
    public void checkArticlesInformationVisible_fromArticlesAdapter() {
        onView(ViewMatchers.withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.articlesImage)).check(matches(isDisplayed()));
        onView(withId(R.id.source)).check(matches(isDisplayed()));
        onView(withId(R.id.information)).check(matches(isDisplayed()));
        onView(withId(R.id.publishedAt)).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

}
