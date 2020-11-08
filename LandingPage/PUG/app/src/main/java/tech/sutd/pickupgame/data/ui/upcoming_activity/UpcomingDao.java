package tech.sutd.pickupgame.data.ui.upcoming_activity;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import tech.sutd.pickupgame.models.ui.UpcomingActivity;
import tech.sutd.pickupgame.models.ui.YourActivity;

@Dao
public interface UpcomingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UpcomingActivity upcomingActivity);

    @Update
    void update(UpcomingActivity upcomingActivity);

    @Delete
    void delete(UpcomingActivity upcomingActivity);

    @Query("DELETE FROM upcoming_activity")
    void deleteAllUpcomingActivities();

    @Query("SELECT * FROM upcoming_activity ORDER BY clock ASC")
    DataSource.Factory<Integer, UpcomingActivity> getAllUpcomingActivitiesByClock();

    @Query("SELECT * FROM upcoming_activity ORDER BY clock ASC LIMIT 2")
    DataSource.Factory<Integer, UpcomingActivity> getUpcomingActivitiesByClockLimit2();
}
