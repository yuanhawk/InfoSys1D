package tech.sutd.pickupgame.ui.auth.register;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import tech.sutd.pickupgame.R;

public class RegisterFragmentDirections {
  private RegisterFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionRegisterFragmentToLoginFragment() {
    return new ActionOnlyNavDirections(R.id.action_registerFragment_to_loginFragment);
  }
}
