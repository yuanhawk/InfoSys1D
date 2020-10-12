package tech.sutd.pickupgame;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionManager {

    private FirebaseAuth fAuth;

    @Inject
    public SessionManager(FirebaseAuth fAuth) {
        this.fAuth = fAuth;
    }

    public FirebaseUser getFirebaseUser() {
        return fAuth.getCurrentUser();
    }
}
