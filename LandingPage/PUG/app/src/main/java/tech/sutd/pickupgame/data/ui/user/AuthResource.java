package tech.sutd.pickupgame.data.ui.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AuthResource<T> {

    @NonNull
    public final AuthStatus status;

    @Nullable
    public final T data;

    @Nullable
    public final String message;


    public AuthResource(@NonNull AuthStatus status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> AuthResource<T> authenticated (@Nullable T data) {
        return new AuthResource<>(AuthStatus.AUTHENTICATED, data, null);
    }

    public static <T> AuthResource<T> error(@NonNull String msg) {
        return new AuthResource<>(AuthStatus.ERROR, null, msg);
    }

    public static <T> AuthResource<T> loading(@Nullable T data) {
        return new AuthResource<>(AuthStatus.LOADING, data, null);
    }

    public static <T> AuthResource<T> registered (@Nullable T data) {
        return new AuthResource<>(AuthStatus.REGISTERED, data, null);
    }

    public static <T> AuthResource<T> logout () {
        return new AuthResource<>(AuthStatus.NOT_AUTHENTICATED, null, null);
    }

    public enum AuthStatus { AUTHENTICATED, ERROR, LOADING, REGISTERED, NOT_AUTHENTICATED }

}