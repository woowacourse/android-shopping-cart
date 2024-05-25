package woowacourse.shopping.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.shopping.data.dao.RecentlyProductDao
import woowacourse.shopping.data.model.RecentlyViewedProductEntity

@Database(
    entities = [RecentlyViewedProductEntity::class],
    version = 1,
)
abstract class RecentlyProductDatabase : RoomDatabase() {
    abstract fun recentlyProductDao(): RecentlyProductDao

    companion object {
        private var instance: RecentlyProductDatabase? = null

        fun getInstance(context: Context): RecentlyProductDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    RecentlyProductDatabase::class.java,
                    "recently_viewed_products",
                ).build().also { instance = it }
            }
        }
    }
}
