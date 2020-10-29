package tech.sutd.pickupgame.ui.auth.gettingstarted;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import tech.sutd.pickupgame.R;

public class GetStartedFragmentDirections {
  private GetStartedFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionGetStartedFragmentToLoginFragment() {
    return new ActionOnlyNavDirections(R.id.action_getStartedFragment_to_loginFragment);
  }
}
