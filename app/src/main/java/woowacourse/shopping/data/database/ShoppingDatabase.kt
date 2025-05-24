package woowacourse.shopping.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.shopping.data.dao.LatestGoodsDao
import woowacourse.shopping.data.dao.ShoppingDao
import woowacourse.shopping.data.entity.LatestGoodsEntity
import woowacourse.shopping.data.entity.ShoppingEntity

@Database(entities = [ShoppingEntity::class, LatestGoodsEntity::class], version = 1)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun shoppingDao(): ShoppingDao

    abstract fun latestGoodsDao(): LatestGoodsDao

    companion object {
        @Volatile
        private var instance: ShoppingDatabase? = null

        fun getDatabase(context: Context): ShoppingDatabase {
            return instance ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        ShoppingDatabase::class.java,
                        "shoppingCart",
                    )
                        .build()
                Companion.instance = instance
                instance
            }
        }
    }
}
