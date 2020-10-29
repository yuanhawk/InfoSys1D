package tech.sutd.pickupgame.data.ui.upcoming_activity;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import tech.sutd.pickupgame.models.ui.UpcomingActivity;

@SuppressWarnings({"unchecked", "deprecation"})
public final class UpcomingDao_Impl implements UpcomingDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UpcomingActivity> __insertionAdapterOfUpcomingActivity;

  private final EntityDeletionOrUpdateAdapter<UpcomingActivity> __deletionAdapterOfUpcomingActivity;

  private final EntityDeletionOrUpdateAdapter<UpcomingActivity> __updateAdapterOfUpcomingActivity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllUpcomingActivities;

  public UpcomingDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUpcomingActivity = new EntityInsertionAdapter<UpcomingActivity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `upcoming_activity` (`id`,`sport`,`clockImg`,`clock`,`locationImg`,`location`,`organizerImg`,`organizer`,`sportImg`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UpcomingActivity value) {
        stmt.bindLong(1, value.getId());
        if (value.getSport() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getSport());
        }
        stmt.bindLong(3, value.getClockImg());
        if (value.getClock() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getClock());
        }
        stmt.bindLong(5, value.getLocationImg());
        if (value.getLocation() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getLocation());
        }
        stmt.bindLong(7, value.getOrganizerImg());
        if (value.getOrganizer() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getOrganizer());
        }
        stmt.bindLong(9, value.getSportImg());
      }
    };
    this.__deletionAdapterOfUpcomingActivity = new EntityDeletionOrUpdateAdapter<UpcomingActivity>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `upcoming_activity` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UpcomingActivity value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfUpcomingActivity = new EntityDeletionOrUpdateAdapter<UpcomingActivity>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `upcoming_activity` SET `id` = ?,`sport` = ?,`clockImg` = ?,`clock` = ?,`locationImg` = ?,`location` = ?,`organizerImg` = ?,`organizer` = ?,`sportImg` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UpcomingActivity value) {
        stmt.bindLong(1, value.getId());
        if (value.getSport() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getSport());
        }
        stmt.bindLong(3, value.getClockImg());
        if (value.getClock() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getClock());
        }
        stmt.bindLong(5, value.getLocationImg());
        if (value.getLocation() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getLocation());
        }
        stmt.bindLong(7, value.getOrganizerImg());
        if (value.getOrganizer() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getOrganizer());
        }
        stmt.bindLong(9, value.getSportImg());
        stmt.bindLong(10, value.getId());
      }
    };
    this.__preparedStmtOfDeleteAllUpcomingActivities = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM upcoming_activity";
        return _query;
      }
    };
  }

  @Override
  public void insert(final UpcomingActivity upcomingActivity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfUpcomingActivity.insert(upcomingActivity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final UpcomingActivity upcomingActivity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfUpcomingActivity.handle(upcomingActivity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final UpcomingActivity upcomingActivity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfUpcomingActivity.handle(upcomingActivity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAllUpcomingActivities() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllUpcomingActivities.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllUpcomingActivities.release(_stmt);
    }
  }

  @Override
  public LiveData<List<UpcomingActivity>> getUpcomingActivities() {
    final String _sql = "SELECT * FROM upcoming_activity";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"upcoming_activity"}, false, new Callable<List<UpcomingActivity>>() {
      @Override
      public List<UpcomingActivity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSport = CursorUtil.getColumnIndexOrThrow(_cursor, "sport");
          final int _cursorIndexOfClockImg = CursorUtil.getColumnIndexOrThrow(_cursor, "clockImg");
          final int _cursorIndexOfClock = CursorUtil.getColumnIndexOrThrow(_cursor, "clock");
          final int _cursorIndexOfLocationImg = CursorUtil.getColumnIndexOrThrow(_cursor, "locationImg");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfOrganizerImg = CursorUtil.getColumnIndexOrThrow(_cursor, "organizerImg");
          final int _cursorIndexOfOrganizer = CursorUtil.getColumnIndexOrThrow(_cursor, "organizer");
          final int _cursorIndexOfSportImg = CursorUtil.getColumnIndexOrThrow(_cursor, "sportImg");
          final List<UpcomingActivity> _result = new ArrayList<UpcomingActivity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final UpcomingActivity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpSport;
            _tmpSport = _cursor.getString(_cursorIndexOfSport);
            final int _tmpClockImg;
            _tmpClockImg = _cursor.getInt(_cursorIndexOfClockImg);
            final String _tmpClock;
            _tmpClock = _cursor.getString(_cursorIndexOfClock);
            final int _tmpLocationImg;
            _tmpLocationImg = _cursor.getInt(_cursorIndexOfLocationImg);
            final String _tmpLocation;
            _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            final int _tmpOrganizerImg;
            _tmpOrganizerImg = _cursor.getInt(_cursorIndexOfOrganizerImg);
            final String _tmpOrganizer;
            _tmpOrganizer = _cursor.getString(_cursorIndexOfOrganizer);
            final int _tmpSportImg;
            _tmpSportImg = _cursor.getInt(_cursorIndexOfSportImg);
            _item = new UpcomingActivity(_tmpId,_tmpSport,_tmpClockImg,_tmpClock,_tmpLocationImg,_tmpLocation,_tmpOrganizerImg,_tmpOrganizer,_tmpSportImg);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }
}
