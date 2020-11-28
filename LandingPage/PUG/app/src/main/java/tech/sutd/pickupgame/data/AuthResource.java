package tech.sutd.pickupgame.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AuthResource<T> {

    @NonNull
    public final AuthStatus status;

    @Nullable
    public final T data;

    @Nullable
    public final String msg;

    public AuthResource(@NonNull AuthStatus status, @Nullable T data, @Nullable String msg) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }

    public static <T> AuthResource<T> success(@Nullable T data) {
        return new AuthResource<>(AuthStatus.SUCCESS, data, null);
    }

    public static <T> AuthResource<T> error(@Nullable String msg) {
        return new AuthResource<>(AuthStatus.ERROR, null, msg);
    }

    public static <T> AuthResource<T> loading(@Nullable T data) {
        return new AuthResource<>(AuthStatus.LOADING, data, null);
    }

    public enum AuthStatus { SUCCESS, ERROR, LOADING}

}
