package tech.sutd.pickupgame.ui.auth.login;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import tech.sutd.pickupgame.R;

public class LoginFragmentDirections {
  private LoginFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionLoginFragmentToRegisterFragment() {
    return new ActionOnlyNavDirections(R.id.action_loginFragment_to_registerFragment);
  }
}
