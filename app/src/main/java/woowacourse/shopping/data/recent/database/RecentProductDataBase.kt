package woowacourse.shopping.data.recent.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.shopping.data.recent.converter.RecentProductConverter
import woowacourse.shopping.data.recent.dao.RecentProductDao
import woowacourse.shopping.data.recent.entity.RecentProduct

@Database(entities = [RecentProduct::class], version = 1)
@TypeConverters(RecentProductConverter::class)
abstract class RecentProductDataBase : RoomDatabase() {
    abstract fun recentProductDao(): RecentProductDao

    companion object {
        @Volatile
        private var instance: RecentProductDataBase? = null

        fun instance(context: Context): RecentProductDataBase {
            return instance ?: synchronized(this) {
                val newInstance =
                    Room.databaseBuilder(context, RecentProductDataBase::class.java, "recent_products").build()
                instance = newInstance
                newInstance
            }
        }
    }
}
