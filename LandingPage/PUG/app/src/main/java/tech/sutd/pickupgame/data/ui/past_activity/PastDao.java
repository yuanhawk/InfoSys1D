package tech.sutd.pickupgame.data.ui.past_activity;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import tech.sutd.pickupgame.models.ui.PastActivity;

@Dao
public interface PastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PastActivity pastActivity);

    @Update
    void update(PastActivity pastActivity);

    @Delete
    void delete(PastActivity pastActivity);

    @Query("DELETE FROM past_activity")
    void deleteAllPastActivities();

    @Query("SELECT * FROM past_activity")
    LiveData<List<PastActivity>> getPastActivities();
}
