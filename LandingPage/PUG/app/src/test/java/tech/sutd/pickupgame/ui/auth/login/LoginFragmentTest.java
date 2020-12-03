package tech.sutd.pickupgame.ui.auth.login;

import org.junit.Test;

import tech.sutd.pickupgame.Utils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class LoginFragmentTest {

    private static final String FAKE_EMAIL = "admin@gmail.com";

    @Test
    public void userIdValidator() {
        assertThat(String.format("Email Validity Test failed for %s ", FAKE_EMAIL),
                Utils.checkEmailForValidity(FAKE_EMAIL), is(true));
    }

}