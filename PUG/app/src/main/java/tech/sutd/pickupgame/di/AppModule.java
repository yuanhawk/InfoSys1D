package tech.sutd.pickupgame.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;

import androidx.work.Constraints;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import tech.sutd.pickupgame.BaseApplication;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.data.SchedulerProvider;
import tech.sutd.pickupgame.data.AppSchedulerProvider;

@Module
public class AppModule {

    @Singleton
    @Provides
    static SharedPreferences provideSharedPreferences() {
        return BaseApplication.getInstance()
                .getSharedPreferences("tech.sutd.pickupgame.sharedpref", Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    static DatabaseReference provideUserReference() {
        return FirebaseDatabase
                .getInstance("https://pickupgame-a91c7.firebaseio.com/")
                .getReference();
    }

    @Singleton
    @Provides
    static FirebaseFirestore provideFirebaseFirestore() {
        return FirebaseFirestore.getInstance();
    }

    @Singleton
    @Provides
    static RequestOptions provideRequestOptions() {
        return RequestOptions
                .placeholderOf(R.drawable.white_background)
                .error(R.drawable.white_background);
    }

    @Singleton
    @Provides
    static RequestManager provideGlideInstance(Application application, RequestOptions requestOptions) {
        return Glide.with(application)
                .setDefaultRequestOptions(requestOptions);
    }

    @Singleton
    @Provides
    static FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Singleton
    @Provides
    static Constraints provideOneTimeWorkRequest() {
        return new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build();
    }

    @Singleton
    @Provides
    static SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Singleton
    @Provides
    static Handler provideHandler() {
        return new Handler(Looper.getMainLooper());
    }

}
