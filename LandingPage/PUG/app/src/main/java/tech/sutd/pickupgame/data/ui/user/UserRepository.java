package tech.sutd.pickupgame.data.ui.user;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import tech.sutd.pickupgame.data.AppExecutors;
import tech.sutd.pickupgame.models.User;

public class UserRepository {

    private final UserDao userDao;
    private final LiveData<List<User>> allUsers;

    public UserRepository(Application application) {
        UserDatabase database = UserDatabase.getInstance(application);
        userDao = database.userDao();
        allUsers = userDao.getUsers();
    }

    public void insert(User user) {
        new InsertUserExecutor(userDao).execute(user);
    }

    public void update(User user) {
        new UpdateUserExecutor(userDao).execute(user);
    }

    public void delete(User user) {
        new DeleteUserExecutor(userDao).execute(user);
    }

    public void deleteAllUsers() {
        new DeleteAllUsersExecutor(userDao).execute();
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    private static class InsertUserExecutor {
        private UserDao userDao;

        public InsertUserExecutor(UserDao userDao) {
            this.userDao = userDao;
        }

        private void execute(User user) {
            AppExecutors.getInstance().getDiskIO().execute(() ->
                    userDao.insert(user));
        }
    }

    private static class UpdateUserExecutor {
        private UserDao userDao;

        public UpdateUserExecutor(UserDao userDao) {
            this.userDao = userDao;
        }

        private void execute(User user) {
            AppExecutors.getInstance().getDiskIO().execute(() ->
                    userDao.update(user));
        }
    }

    private static class DeleteUserExecutor {
        private UserDao userDao;

        public DeleteUserExecutor(UserDao userDao) {
            this.userDao = userDao;
        }

        private void execute(User user) {
            AppExecutors.getInstance().getDiskIO().execute(() ->
                    userDao.delete(user));
        }
    }

    private static class DeleteAllUsersExecutor {
        private UserDao userDao;

        public DeleteAllUsersExecutor(UserDao userDao) {
            this.userDao = userDao;
        }

        private void execute() {
            AppExecutors.getInstance().getDiskIO().execute(() ->
                    userDao.deleteAllUsers());
        }
    }
}
