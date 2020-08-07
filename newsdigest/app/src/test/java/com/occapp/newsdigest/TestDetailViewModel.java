package com.occapp.newsdigest;

import android.text.TextUtils;

import com.occapp.newsdigest.activities.detail.DetailViewModel;
import com.occapp.newsdigest.network.model.NewsArticles;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;


@RunWith(PowerMockRunner.class)
@PrepareForTest({TextUtils.class})
public class TestDetailViewModel {

    @InjectMocks
    DetailViewModel detailViewModel;

    @Mock
    NewsArticles articles;

    @Test
    public void stringValidator_returnTrueIfNullOrEmpty() {
        detailViewModel = spy(detailViewModel);
        Mockito.when(detailViewModel.isStringEmpty("testing")).thenReturn(false);
        assertEquals(true, detailViewModel.isStringEmpty(null));
        assertEquals(true, detailViewModel.isStringEmpty(""));//digit.size()=10
    }

    @Test
    public void stringValidator_returnFalseIfNotNullOrNotEmpty() {
        detailViewModel = spy(detailViewModel);
        Mockito.when(detailViewModel.isStringEmpty("testing")).thenReturn(false);
        assertEquals(false, detailViewModel.isStringEmpty("testing"));
    }

    @Test
    public void isSharedObjectNull() {
        detailViewModel = spy(detailViewModel);
        detailViewModel.init(null);
        Mockito.when(detailViewModel.isSharedObjectNull()).thenReturn(true);
        assertEquals(true, detailViewModel.isSharedObjectNull());
    }

    @Test
    public void isSharedObjectNotNull() {
        detailViewModel = spy(detailViewModel);
        detailViewModel.init(articles);
        Mockito.when(detailViewModel.isSharedObjectNull()).thenReturn(false);
        assertEquals(false, detailViewModel.isSharedObjectNull());

    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(TextUtils.class);
        PowerMockito.when(TextUtils.isEmpty(any(CharSequence.class)))
                .thenAnswer((Answer<Boolean>) invocation -> {
                    CharSequence a = (CharSequence) invocation.getArguments()[0];
                    return !(a != null && a.length() > 0);
                });
    }
}