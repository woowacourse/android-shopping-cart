package woowacourse.shopping.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.shopping.data.converter.LocalDateTimeConverter
import woowacourse.shopping.data.dao.RecentProductDao
import woowacourse.shopping.data.dao.ShoppingCartItemDao
import woowacourse.shopping.data.entity.RecentProductEntity
import woowacourse.shopping.data.entity.ShoppingCartItemEntity

@Database(
    entities = [RecentProductEntity::class, ShoppingCartItemEntity::class],
    version = 1,
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class ShoppingDataBase : RoomDatabase() {
    abstract fun recentProductDao(): RecentProductDao

    abstract fun shoppingCartItemDao(): ShoppingCartItemDao

    companion object {
        @Volatile
        private var instance: ShoppingDataBase? = null

        fun database(context: Context): ShoppingDataBase =
            instance ?: synchronized(this) {
                val newInstance =
                    buildDatabase(context)
                instance = newInstance
                return newInstance
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ShoppingDataBase::class.java,
                "shopping",
            ).build()
    }
}
