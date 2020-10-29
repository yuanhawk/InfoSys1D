package tech.sutd.pickupgame.data.ui.upcoming_activity;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class UpcomingDatabase_Impl extends UpcomingDatabase {
  private volatile UpcomingDao _upcomingDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `upcoming_activity` (`id` INTEGER NOT NULL, `sport` TEXT, `clockImg` INTEGER NOT NULL, `clock` TEXT, `locationImg` INTEGER NOT NULL, `location` TEXT, `organizerImg` INTEGER NOT NULL, `organizer` TEXT, `sportImg` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'db3d3459f83626534f2b517a31f107d4')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `upcoming_activity`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsUpcomingActivity = new HashMap<String, TableInfo.Column>(9);
        _columnsUpcomingActivity.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUpcomingActivity.put("sport", new TableInfo.Column("sport", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUpcomingActivity.put("clockImg", new TableInfo.Column("clockImg", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUpcomingActivity.put("clock", new TableInfo.Column("clock", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUpcomingActivity.put("locationImg", new TableInfo.Column("locationImg", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUpcomingActivity.put("location", new TableInfo.Column("location", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUpcomingActivity.put("organizerImg", new TableInfo.Column("organizerImg", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUpcomingActivity.put("organizer", new TableInfo.Column("organizer", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUpcomingActivity.put("sportImg", new TableInfo.Column("sportImg", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUpcomingActivity = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUpcomingActivity = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUpcomingActivity = new TableInfo("upcoming_activity", _columnsUpcomingActivity, _foreignKeysUpcomingActivity, _indicesUpcomingActivity);
        final TableInfo _existingUpcomingActivity = TableInfo.read(_db, "upcoming_activity");
        if (! _infoUpcomingActivity.equals(_existingUpcomingActivity)) {
          return new RoomOpenHelper.ValidationResult(false, "upcoming_activity(tech.sutd.pickupgame.models.ui.UpcomingActivity).\n"
                  + " Expected:\n" + _infoUpcomingActivity + "\n"
                  + " Found:\n" + _existingUpcomingActivity);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "db3d3459f83626534f2b517a31f107d4", "6cc58d4c7b493bb34b665a11d7a27627");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "upcoming_activity");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `upcoming_activity`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public UpcomingDao upcomingDao() {
    if (_upcomingDao != null) {
      return _upcomingDao;
    } else {
      synchronized(this) {
        if(_upcomingDao == null) {
          _upcomingDao = new UpcomingDao_Impl(this);
        }
        return _upcomingDao;
      }
    }
  }
}
