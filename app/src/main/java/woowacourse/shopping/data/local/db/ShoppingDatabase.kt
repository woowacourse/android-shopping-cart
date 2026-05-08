package woowacourse.shopping.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.shopping.data.local.converter.PriceConverter
import woowacourse.shopping.data.local.dao.CartDao
import woowacourse.shopping.data.local.dao.ProductDao
import woowacourse.shopping.data.local.entity.CartItemEntity
import woowacourse.shopping.data.local.entity.ProductEntity

@Database(entities = [ProductEntity::class, CartItemEntity::class], version = 1)
@TypeConverters(PriceConverter::class)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
}
