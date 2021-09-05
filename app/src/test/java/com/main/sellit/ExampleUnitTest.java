package com.main.sellit;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.main.sellit.contract.LoginContract;
import com.main.sellit.helper.ApiUrls;
import com.main.sellit.network.VolleyController;
import com.main.sellit.presenter.LoginPresenter;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

    @Mock
    LoginContract.Presenter presenter;
    @Mock
    LoginContract.LoginView loginView;


    @Mock
    Context context;

    @Mock
    VolleyController volleyController;

    @Before
    public void setUp() throws Exception {
        presenter = new LoginPresenter(loginView, context);
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


}