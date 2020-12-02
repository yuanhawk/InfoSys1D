package tech.sutd.pickupgame.data;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import tech.sutd.pickupgame.data.ui.helper.NewRoomHelper;
import tech.sutd.pickupgame.data.ui.helper.PastRoomHelper;
import tech.sutd.pickupgame.data.ui.helper.UpcomingRoomHelper;
import tech.sutd.pickupgame.data.ui.helper.UserRoomHelper;
import tech.sutd.pickupgame.data.ui.helper.YourRoomHelper;
import tech.sutd.pickupgame.models.User;
import tech.sutd.pickupgame.models.ui.NewActivity;
import tech.sutd.pickupgame.models.ui.PastActivity;
import tech.sutd.pickupgame.models.ui.UpcomingActivity;
import tech.sutd.pickupgame.models.ui.YourActivity;

public class AppDataManager implements DataManager {

    private final YourRoomHelper yourRoomHelper;
    private final UserRoomHelper userRoomHelper;
    private final NewRoomHelper newRoomHelper;
    private final UpcomingRoomHelper upcomingRoomHelper;
    private final PastRoomHelper pastRoomHelper;

    @Inject
    public AppDataManager(YourRoomHelper yourRoomHelper, UserRoomHelper userRoomHelper,
                          NewRoomHelper newRoomHelper, UpcomingRoomHelper upcomingRoomHelper,
                          PastRoomHelper pastRoomHelper) {
        this.yourRoomHelper = yourRoomHelper;
        this.userRoomHelper = userRoomHelper;
        this.newRoomHelper = newRoomHelper;
        this.upcomingRoomHelper = upcomingRoomHelper;
        this.pastRoomHelper = pastRoomHelper;
    }

    @Override
    public Completable insertYourActivity(YourActivity activity) {
        return yourRoomHelper.insertYourActivity(activity);
    }

    public Completable deleteAllYourActivities() {
        return yourRoomHelper.deleteAllYourActivities();
    }

    @Override
    public Flowable<List<YourActivity>> getAllYourActivitiesLimit10() {
        return yourRoomHelper.getAllYourActivitiesLimit10();
    }

    @Override
    public Flowable<List<YourActivity>> getAllYourActivities() {
        return yourRoomHelper.getAllYourActivities();
    }

    @Override
    public Completable insertUser(User user) {
        return userRoomHelper.insertUser(user);
    }

    @Override
    public Completable deleteAllUsers() {
        return userRoomHelper.deleteAllUsers();
    }

    @Override
    public LiveData<List<User>> getAllUsers() {
        return userRoomHelper.getAllUsers();
    }



    @Override
    public Completable insertNewActivity(NewActivity newActivity) {
        return newRoomHelper.insertNewActivity(newActivity);
    }

    @Override
    public Completable deleteNewActivityByClock(String clock) {
        return newRoomHelper.deleteNewActivityByClock(clock);
    }

    @Override
    public Flowable<PagedList<NewActivity>> getAllNewActivitiesByClock() {
        return newRoomHelper.getAllNewActivitiesByClock();
    }

    @Override
    public Flowable<PagedList<NewActivity>> getAllNewActivitiesBySport() {
        return newRoomHelper.getAllNewActivitiesBySport();
    }

    @Override
    public Flowable<PagedList<NewActivity>> getNewActivitiesByClock2() {
        return newRoomHelper.getNewActivitiesByClock2();
    }



    @Override
    public Completable insertUpcomingActivity(UpcomingActivity upcomingActivity) {
        return upcomingRoomHelper.insertUpcomingActivity(upcomingActivity);
    }

    @Override
    public Completable deleteUpcomingActivities(String clock) {
        return upcomingRoomHelper.deleteUpcomingActivities(clock);
    }

    @Override
    public Completable deleteUpcomingActivitiesById(String id) {
        return upcomingRoomHelper.deleteUpcomingActivitiesById(id);
    }

    @Override
    public Flowable<PagedList<UpcomingActivity>> getAllUpcomingActivitiesByClock() {
        return upcomingRoomHelper.getAllUpcomingActivitiesByClock();
    }

    @Override
    public Flowable<PagedList<UpcomingActivity>> getUpcomingActivitiesByClockLimit2() {
        return upcomingRoomHelper.getUpcomingActivitiesByClockLimit2();
    }



    @Override
    public Completable insertPastActivity(PastActivity pastActivity) {
        return pastRoomHelper.insertPastActivity(pastActivity);
    }

    @Override
    public Completable deleteAllPastActivities() {
        return pastRoomHelper.deleteAllPastActivities();
    }

    @Override
    public Flowable<List<PastActivity>> getAllPastActivities() {
        return pastRoomHelper.getAllPastActivities();
    }
}
