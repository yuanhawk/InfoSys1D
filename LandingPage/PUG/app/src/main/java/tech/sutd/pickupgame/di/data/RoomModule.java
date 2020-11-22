package tech.sutd.pickupgame.di.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import tech.sutd.pickupgame.data.ui.new_activity.NewDatabase;
import tech.sutd.pickupgame.data.ui.new_activity.NewRepository;
import tech.sutd.pickupgame.di.helper.NewRoomHelper;

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

}
