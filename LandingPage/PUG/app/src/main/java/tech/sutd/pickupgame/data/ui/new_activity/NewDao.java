package tech.sutd.pickupgame.data.ui.new_activity;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import tech.sutd.pickupgame.models.ui.NewActivity;

@Dao
public interface NewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NewActivity newActivity);

    @Update
    void update(NewActivity newActivity);

    @Delete
    void delete(NewActivity newActivity);

    @Query("DELETE FROM new_activity")
    void deleteAllNewActivities();

    @Query("SELECT * FROM new_activity")
    LiveData<List<NewActivity>> getNewActivities();

}
