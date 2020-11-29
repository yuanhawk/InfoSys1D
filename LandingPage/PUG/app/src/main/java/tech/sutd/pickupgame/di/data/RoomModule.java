package tech.sutd.pickupgame.di.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import tech.sutd.pickupgame.data.AppExecutors;
import tech.sutd.pickupgame.data.DataManager;
import tech.sutd.pickupgame.data.ui.helper.PastRoomHelper;
import tech.sutd.pickupgame.data.ui.helper.UpcomingRoomHelper;
import tech.sutd.pickupgame.data.ui.helper.UserRoomHelper;
import tech.sutd.pickupgame.data.ui.new_activity.NewRoom;
import tech.sutd.pickupgame.data.ui.past_activity.PastRoom;
import tech.sutd.pickupgame.data.ui.upcoming_activity.UpcomingRoom;
import tech.sutd.pickupgame.data.ui.user.UserDao;
import tech.sutd.pickupgame.data.ui.user.UserDatabase;
import tech.sutd.pickupgame.data.ui.user.UserRoom;
import tech.sutd.pickupgame.data.ui.your_activity.YourRoom;
import tech.sutd.pickupgame.data.AppDataManager;
import tech.sutd.pickupgame.data.ui.helper.YourRoomHelper;
import tech.sutd.pickupgame.data.ui.your_activity.YourDatabase;
import tech.sutd.pickupgame.data.ui.helper.NewRoomHelper;
import tech.sutd.pickupgame.models.User;

@Module(includes = {RoomDatabaseModule.class})
public class RoomModule {

    @Singleton
    @Provides
    static DataManager provideDataManager(YourRoomHelper yourRoomHelper, UserRoomHelper userRoomHelper,
                                          NewRoomHelper newRoomHelper, UpcomingRoomHelper upcomingRoomHelper,
                                          PastRoomHelper pastRoomHelper) {
        return new AppDataManager(yourRoomHelper, userRoomHelper, newRoomHelper, upcomingRoomHelper, pastRoomHelper);
    }

    @Singleton
    @Provides
    static NewRoomHelper provideNewRoomHelper(NewRoom helper) {
        return helper;
    }

    @Singleton
    @Provides
    static YourRoomHelper provideYourRoomHelper(YourRoom helper) {
        return helper;
    }

    @Singleton
    @Provides
    static UserRoomHelper provideUserRoomHelper(UserRoom helper) {
        return helper;
    }

    @Singleton
    @Provides
    static UpcomingRoomHelper provideUpcomingRoomHelper(UpcomingRoom helper) {
        return helper;
    }

    @Singleton
    @Provides
    static PastRoomHelper pastRoomHelper(PastRoom helper) {
        return helper;
    }
}
