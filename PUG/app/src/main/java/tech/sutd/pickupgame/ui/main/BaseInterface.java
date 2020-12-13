package tech.sutd.pickupgame.ui.main;

import tech.sutd.pickupgame.ui.main.main.MainFragment;
import tech.sutd.pickupgame.ui.main.main.newact.NewActFragment;
import tech.sutd.pickupgame.ui.main.main.upcomingact.UpcomingActFragment;

public class BaseInterface {

    public interface CustomActListener {
        void customAction();
    }

    public interface BookingActListener {
        void onSignUpSuccess(MainFragment mainFragment, NewActFragment newFragment);
        void onSignUpFailure();
    }

    public interface UpcomingActDeleteListener {
        void onDeleteSuccess(MainFragment mainFragment, UpcomingActFragment upcomingFragment);
        void onDeleteFailure();
    }

    public interface RefreshListener {
        void refreshObserver();
    }

}
