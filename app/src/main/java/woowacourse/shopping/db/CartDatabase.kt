package woowacourse.shopping.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.shopping.db.cartItem.CartItemDao
import woowacourse.shopping.db.cartItem.CartItemEntity
import woowacourse.shopping.db.recenteProduct.RecentlyViewedProductDao
import woowacourse.shopping.db.recenteProduct.RecentlyViewedProductEntity

@Database(
    entities = [CartItemEntity::class, RecentlyViewedProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartItemDao(): CartItemDao
    abstract fun recentlyViewedProductDao(): RecentlyViewedProductDao

    companion object {
        @Volatile
        private var INSTANCE: CartDatabase? = null

        fun getDatabase(context: Context): CartDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CartDatabase::class.java,
                    "cart_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
