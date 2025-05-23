package woowacourse.shopping.data.cart

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.data.product.ProductEntity

@Database(entities = [ProductEntity::class, CartItemEntity::class], version = 3)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun cartItemDao(): CartItemDao
}
