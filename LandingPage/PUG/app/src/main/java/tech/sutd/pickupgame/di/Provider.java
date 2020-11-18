package tech.sutd.pickupgame.di;

public class Provider {

    private static AppComponent appComponent;

    public static void setAppComponent(AppComponent component) {
        appComponent = component;
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
