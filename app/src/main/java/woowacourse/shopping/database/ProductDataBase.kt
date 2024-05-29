package woowacourse.shopping.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.shopping.database.recentviewedproducts.RecentlyViewedProductEntity
import woowacourse.shopping.database.recentviewedproducts.RecentlyViewedProductsDao
import woowacourse.shopping.database.shoppingcart.ShoppingCartDao
import woowacourse.shopping.database.shoppingcart.ShoppingCartItemEntity

@Database(entities = [RecentlyViewedProductEntity::class, ShoppingCartItemEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class ProductDataBase : RoomDatabase() {
    abstract fun recentlyViewedProductDao(): RecentlyViewedProductsDao

    abstract fun shoppingCartDao(): ShoppingCartDao

    companion object {
        @Volatile
        private var Instance: ProductDataBase? = null

        @Synchronized
        fun getDatabase(context: Context): ProductDataBase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    ProductDataBase::class.java,
                    "product_database",
                ).build().also { Instance = it }
            }
        }
    }
}
