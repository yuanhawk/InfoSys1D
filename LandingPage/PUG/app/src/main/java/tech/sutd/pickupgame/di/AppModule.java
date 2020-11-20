package tech.sutd.pickupgame.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import tech.sutd.pickupgame.BaseApplication;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.data.worker.NewActivitiesWorker;

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
    static Drawable provideLoginDrawable(Application application) {
        return ContextCompat.getDrawable(application, R.drawable.ic_pug);
    }

    @Singleton
    @Provides
    static FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Singleton
    @Provides
    static MessageDigest provideMessageDigest() {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md;
    }

    @Singleton
    @Provides
    static PeriodicWorkRequest providePeriodicWorkRequest() {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresBatteryNotLow(true)
                .build();

        return new PeriodicWorkRequest.Builder(NewActivitiesWorker.class,
                1, TimeUnit.HOURS,
                15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build();
    }

    @Singleton
    @Provides
    static OneTimeWorkRequest provideOneTimeWorkRequest() {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresBatteryNotLow(true)
                .build();

        return new OneTimeWorkRequest.Builder(NewActivitiesWorker.class)
                .setConstraints(constraints)
                .build();
    }

}
