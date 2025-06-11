package woowacourse.shopping.data.shoppingcart.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.shopping.data.recentgoods.database.RecentGoodsDao
import woowacourse.shopping.data.recentgoods.database.RecentGoodsEntity

@Database(entities = [ShoppingCartItemEntity::class, RecentGoodsEntity::class], version = 1)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun shoppingCartDao(): ShoppingCartDao

    abstract fun recentGoodsDao(): RecentGoodsDao

    companion object {
        @Volatile
        private var INSTANCE: ShoppingDatabase? = null

        fun getDatabase(context: Context): ShoppingDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    ShoppingDatabase::class.java,
                    "Shopping",
                ).build().also { INSTANCE = it }
            }
        }
    }
}
