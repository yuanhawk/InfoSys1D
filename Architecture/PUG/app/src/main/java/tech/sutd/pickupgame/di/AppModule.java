package tech.sutd.pickupgame.di;

import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Singleton
    @Provides
    static FirebaseDatabase provideFirebaseDatabase() {
        return FirebaseDatabase.getInstance();
    }

}
