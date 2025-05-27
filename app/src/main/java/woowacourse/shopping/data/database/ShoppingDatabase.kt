package woowacourse.shopping.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.shopping.data.converter.Converters
import woowacourse.shopping.data.dao.CartDao
import woowacourse.shopping.data.dao.RecentProductDao
import woowacourse.shopping.data.entity.CartEntity
import woowacourse.shopping.data.entity.RecentProductEntity

@Database(entities = [CartEntity::class, RecentProductEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    abstract fun recentProductDao(): RecentProductDao

    companion object {
        @Volatile
        private var INSTANCE: ShoppingDatabase? = null

        fun getDatabase(context: Context): ShoppingDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        ShoppingDatabase::class.java,
                        "word_database",
                    ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
