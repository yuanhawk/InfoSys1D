package tech.sutd.pickupgame.ui.auth.gettingstarted;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.ui.auth.AuthActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class GetStartedFragmentTest {

    @Rule
    public ActivityTestRule<AuthActivity> rule = new ActivityTestRule<>(AuthActivity.class);

    @Before
    public void init() {
        rule.getActivity()
                .getSupportFragmentManager()
                .beginTransaction();
    }

    @Test
    public void testGetStartedFragment() {
        onView(withId(R.id.get_started_layout)).check(matches(isDisplayed()));
    }

}