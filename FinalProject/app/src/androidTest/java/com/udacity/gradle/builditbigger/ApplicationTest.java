package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;
import android.test.InstrumentationTestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private static boolean called;

    private MainActivity activity;
    private Context mContext = null;

    public ApplicationTest(Class<MainActivity> activityClass) {
        super(activityClass);
    }

    protected void setUp() throws Exception {
        super.setUp();
        called = false;

        activity = getActivity();
        mContext = activity;
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public final void testSuccessfulFetch() throws Throwable {
        final CountDownLatch signal = new CountDownLatch(1);

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                new MainActivityFragment.EndpointsAsyncTask(mContext).execute(new Pair<Context, String>(mContext, "Test")).execute();
                called = true;
                signal.countDown();
            }
        });

        signal.await(10, TimeUnit.SECONDS);
        assertTrue(called);
    }
}