package tech.sutd.pickupgame.ui.auth.login;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.ui.auth.AuthActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

public class LoginFragmentTest {

    private Context appContext;

    @Rule
    public ActivityScenarioRule<AuthActivity> rule = new ActivityScenarioRule<>(AuthActivity.class);

    @Before
    public void init() {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        rule.getScenario().onActivity(activity -> activity.getSupportFragmentManager()
                .beginTransaction().add(R.id.nav_host_fragment, new LoginFragment()).commit()
        );
    }

    @Test
    public void testContext() {
        assertEquals("tech.sutd.pickupgame", appContext.getPackageName());
    }

    @Test
    public void testLoginFragment() {
        onView(withId(R.id.signIn)).check(matches(isDisplayed()));
        onView(withId(R.id.myRectangleView1)).check(matches(isDisplayed()));
        onView(withId(R.id.signUp)).check(matches(isDisplayed()));
        onView(withId(R.id.myRectangleView2)).check(matches(isDisplayed()));
        onView(withId(R.id.userId)).check(matches(isDisplayed()));
        onView(withId(R.id.passwd)).check(matches(isDisplayed()));
        onView(withId(R.id.forgot_password)).check(matches(isDisplayed()));
        onView(withId(R.id.remember_me)).check(matches(isDisplayed()));
        onView(withId(R.id.loginCV)).check(matches(isDisplayed()));
        onView(withId(R.id.login)).check(matches(isDisplayed()));
    }

    @Test
    public void testNotVisibleLoginFragment() {
        onView(withId(R.id.progress)).check(matches(not(isDisplayed())));
    }

}