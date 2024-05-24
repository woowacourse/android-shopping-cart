package woowacourse.shopping.model.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [OrderEntity::class, RecentProductEntity::class], version = 2)
@TypeConverters(Converters::class)
abstract class AlsongDatabase : RoomDatabase() {
    abstract fun orderDao(): OrderDao

    abstract fun recentProductDao(): RecentProductDao

    companion object {
        @Volatile
        private var instance: AlsongDatabase? = null

        fun getDatabase(context: Context): AlsongDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, AlsongDatabase::class.java, "alsong.db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}
