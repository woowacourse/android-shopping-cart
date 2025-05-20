package woowacourse.shopping.data.cart

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.data.product.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}
