package woowacourse.shopping.data.db.producthistory

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RecentProduct::class], version = 1)
abstract class RecentProductDatabase : RoomDatabase() {
    abstract fun productHistoryDao(): RecentProductDao

    companion object {
        @Volatile
        private var instance: RecentProductDatabase? = null
        private const val RECENT_PRODUCT_DATABASE = "recentProductDatabase"

        fun getInstance(context: Context): RecentProductDatabase {
            var instance = instance

            if (instance == null) {
                instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        RecentProductDatabase::class.java,
                        RECENT_PRODUCT_DATABASE,
                    ).build()
            }

            return instance
        }
    }
}
