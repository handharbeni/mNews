package com.mdirect.mnews;

import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Beni on 26/03/2018.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule public final ActivityRule<MainActivity> main = new ActivityRule<>(MainActivity.class);

    @Test
    public void shouldBeAbleToLaunchMainScreen(){
        onView(withId(R.id.tabLayout)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.pager)).check(ViewAssertions.matches(isDisplayed()));
//        onView(withText("Semua Kanal")).check(ViewAssertions.matches(isDisplayed()));
//        onView(withText("Fokus")).check(ViewAssertions.matches(isDisplayed()));
//        onView(withText("LOGIN"));
    }

}
