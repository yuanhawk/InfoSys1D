package tech.sutd.pickupgame.ui.main;

public class BaseInterface {

    public interface CustomActListener {
        void customAction();
    }

    public interface BookingActListener {
        void onSignUpSuccess();
        void onSignUpFailure();
    }

    public interface UpcomingActDeleteListener {
        void onDeleteSuccess();
        void onDeleteFailure();
    }

}
