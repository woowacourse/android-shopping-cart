package woowacourse.shopping.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.shopping.data.cart.CartDao
import woowacourse.shopping.data.cart.CartItemEntity
import woowacourse.shopping.data.recentProducts.RecentProductDao
import woowacourse.shopping.data.recentProducts.RecentProductEntity

@Database(entities = [RecentProductEntity::class, CartItemEntity::class], version = 3)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    abstract fun recentProductDao(): RecentProductDao

    companion object {
        @Volatile
        private var INSTANCE: ShoppingDatabase? = null

        fun getDatabase(context: Context): ShoppingDatabase =
            INSTANCE ?: synchronized(this) {
                val instance =
                    Room
                        .databaseBuilder(
                            context.applicationContext,
                            ShoppingDatabase::class.java,
                            "shopping_cart_db",
                        ).fallbackToDestructiveMigration()
                        .build()

                INSTANCE = instance
                instance
            }
    }
}
