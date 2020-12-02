package tech.sutd.pickupgame.data.ui.new_activity;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import tech.sutd.pickupgame.models.ui.NewActivity;
import tech.sutd.pickupgame.models.ui.PastActivity;

@Dao
public interface NewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertNewActivity(NewActivity newActivity);

    @Query("DELETE FROM new_activity WHERE id = :id")
    Completable deleteNewActivityById(String id);

    @Query("DELETE FROM new_activity WHERE clock < :clock")
    Completable deleteNewActivityByClock(String clock);

    @Query("SELECT * FROM new_activity ORDER BY clock ASC")
    DataSource.Factory<Integer, NewActivity> getAllNewActivitiesByClock();

    @Query("SELECT * FROM new_activity ORDER BY sport ASC")
    DataSource.Factory<Integer, NewActivity> getAllNewActivitiesBySport();

    @Query("SELECT * FROM new_activity ORDER BY clock ASC LIMIT 2")
    DataSource.Factory<Integer, NewActivity> getNewActivitiesByClockLimit2();
}
