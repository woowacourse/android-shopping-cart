package woowacourse.shopping.model.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [RecentProductEntity::class], version = 3)
@TypeConverters(Converters::class)
abstract class RecentProductDatabase : RoomDatabase() {
    abstract fun recentProductDao(): RecentProductDao

    companion object {
        @Volatile
        private var instance: RecentProductDatabase? = null

        fun getDatabase(context: Context): RecentProductDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, RecentProductDatabase::class.java, "alsong.db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}
