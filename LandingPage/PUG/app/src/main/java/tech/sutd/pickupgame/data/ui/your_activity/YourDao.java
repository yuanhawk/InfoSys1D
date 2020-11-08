package tech.sutd.pickupgame.data.ui.your_activity;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import tech.sutd.pickupgame.models.ui.YourActivity;

@Dao
public interface YourDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(YourActivity yourActivity);

    @Update
    void update(YourActivity yourActivity);

    @Delete
    void delete(YourActivity yourActivity);

    @Query("DELETE FROM your_activity")
    void deleteYourActivities();

    @Query("SELECT * FROM your_activity ORDER BY clock DESC LIMIT 10")
    LiveData<List<YourActivity>> getYourActivities();
}
