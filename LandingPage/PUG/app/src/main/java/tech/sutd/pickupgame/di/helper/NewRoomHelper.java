package tech.sutd.pickupgame.di.helper;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.PagedList;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import tech.sutd.pickupgame.models.ui.NewActivity;

public interface NewRoomHelper {

    void insert(NewActivity newActivity);
    void update(NewActivity newActivity);
    void delete(String clock);
    void deleteAllNewActivities();
    LiveData<PagedList<NewActivity>> getAllNewActivitiesByClock();
    LiveData<PagedList<NewActivity>> getAllNewActivitiesBySport();
    LiveData<PagedList<NewActivity>> getNewActivitiesByClock2();

}
