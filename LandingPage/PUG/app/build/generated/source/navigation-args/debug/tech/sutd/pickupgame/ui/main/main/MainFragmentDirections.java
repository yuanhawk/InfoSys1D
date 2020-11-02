package tech.sutd.pickupgame.ui.main.main;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import tech.sutd.pickupgame.R;

public class MainFragmentDirections {
  private MainFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionMainFragmentToUpcomingActFragment() {
    return new ActionOnlyNavDirections(R.id.action_mainFragment_to_upcomingActFragment);
  }
}
