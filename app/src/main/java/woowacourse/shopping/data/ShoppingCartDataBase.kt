package woowacourse.shopping.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.shopping.data.cart.converter.CartItemConverter
import woowacourse.shopping.data.cart.dao.CartDao
import woowacourse.shopping.data.cart.entity.CartItem
import woowacourse.shopping.data.product.dao.ProductDao
import woowacourse.shopping.data.product.entity.Product
import woowacourse.shopping.data.recent.converter.RecentProductConverter
import woowacourse.shopping.data.recent.dao.RecentProductDao
import woowacourse.shopping.data.recent.entity.RecentProduct

@Database(entities = [CartItem::class, Product::class, RecentProduct::class], version = 1)
@TypeConverters(CartItemConverter::class, RecentProductConverter::class)
abstract class ShoppingCartDataBase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    abstract fun productDao(): ProductDao

    abstract fun recentProductDao(): RecentProductDao

    companion object {
        @Volatile
        private var instance: ShoppingCartDataBase? = null

        fun instance(context: Context): ShoppingCartDataBase {
            return instance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(context, ShoppingCartDataBase::class.java, "shopping_cart").build()
                instance = newInstance
                newInstance
            }
        }
    }
}
