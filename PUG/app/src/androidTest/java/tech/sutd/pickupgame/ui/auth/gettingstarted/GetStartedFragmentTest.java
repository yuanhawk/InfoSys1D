package tech.sutd.pickupgame.ui.auth.gettingstarted;

import android.content.Context;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.ui.auth.AuthActivity;
import tech.sutd.pickupgame.ui.auth.login.LoginFragment;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4ClassRunner.class)
public class GetStartedFragmentTest {

    private Context appContext;

    @Rule
    public ActivityScenarioRule<AuthActivity> rule = new ActivityScenarioRule<>(AuthActivity.class);

    @Before
    public void init() {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        rule.getScenario().onActivity(activity -> activity.getSupportFragmentManager()
                .beginTransaction());
    }

    @Test
    public void testContext() {
        assertEquals("tech.sutd.pickupgame", appContext.getPackageName());
    }

    @Test
    public void testGetStartedFragment() {
        onView(withId(R.id.pug_title)).check(matches(isDisplayed()));
        onView(withId(R.id.image_view)).check(matches(isDisplayed()));
        onView(withId(R.id.get_started_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void testClick() {
        onView(withId(R.id.get_started)).check(matches(isClickable()));
    }

    @Test
    public void testNavigationFromGetStartedToLoginFragment() {
        TestNavHostController navHostController = new TestNavHostController(
                ApplicationProvider.getApplicationContext());
        navHostController.setGraph(R.navigation.auth);

        FragmentScenario<GetStartedFragment> scenario =
                FragmentScenario.launchInContainer(GetStartedFragment.class);
        scenario.onFragment(fragment ->
            Navigation.setViewNavController(fragment.requireView(), navHostController)
        );

        onView(withId(R.id.get_started)).perform(click());
        assertThat(navHostController.getCurrentDestination().getId()).isEqualTo(R.id.loginFragment);
    }

}