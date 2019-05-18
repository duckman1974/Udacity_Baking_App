package com.example.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    public static final String FIRST_RECIPE = "Nutella Pie";

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkRecipeName() {
        //onData(anything()).in inAdapterView(withId(R.id.recipeNameText)).atPosition(1).perform(click());
        onView(withId(R.id.listRecyclerView)).perform(RecyclerViewActions.scrollToPosition(1));
        onView(withText(FIRST_RECIPE)).check(matches(isDisplayed()));
    }
}
