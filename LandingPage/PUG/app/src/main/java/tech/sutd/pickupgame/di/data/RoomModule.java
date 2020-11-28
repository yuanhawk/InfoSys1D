package tech.sutd.pickupgame.di.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;

import dagger.Module;
import dagger.Provides;
import tech.sutd.pickupgame.data.DataManager;
import tech.sutd.pickupgame.data.ui.new_activity.NewDatabase;
import tech.sutd.pickupgame.data.ui.new_activity.NewRepository;
import tech.sutd.pickupgame.data.ui.your_activity.YourRoom;
import tech.sutd.pickupgame.data.AppDataManager;
import tech.sutd.pickupgame.data.ui.helper.YourRoomHelper;
import tech.sutd.pickupgame.data.ui.your_activity.YourDatabase;
import tech.sutd.pickupgame.data.ui.helper.NewRoomHelper;

@Module
public class RoomModule {

    @Provides
    static NewRoomHelper provideNewRoomHelper(NewRepository repository) {
        return repository;
    }

    @NonNull
    @Provides
    static NewDatabase provideNewDatabase(Application application) {
        return Room.databaseBuilder(application,
                NewDatabase.class, "new_activities_database")
                .build();
    }

    @NonNull
    @Provides
    static YourDatabase provideYourDatabase(Application application) {
        return Room.databaseBuilder(application,
                YourDatabase.class, "your_activities_database")
                .build();
    }

    @Provides
    static DataManager provideDataManager(YourRoomHelper helper) {
        return new AppDataManager(helper);
    }

    @Provides
    static YourRoomHelper provideRoomHelper(YourRoom helper) {
        return helper;
    }

}
