package tech.sutd.pickupgame.di;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.models.User;

@Module
public class AppModule {

    @Singleton
    @Provides
    static DatabaseReference provideUserReference() {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://pickupgame-a91c7.firebaseio.com/");
        db.setPersistenceEnabled(true);

        DatabaseReference reff = db.getReference();
        reff.keepSynced(true);
        return reff;
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
        return ContextCompat.getDrawable(application, R.drawable.pug);
    }

    @Singleton
    @Provides
    static FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }
}
