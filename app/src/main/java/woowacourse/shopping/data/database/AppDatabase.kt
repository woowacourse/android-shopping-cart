package woowacourse.shopping.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.data.dao.CartDao
import woowacourse.shopping.data.dao.LastProductDao
import woowacourse.shopping.data.dao.ProductDao
import woowacourse.shopping.data.entity.CartEntity
import woowacourse.shopping.data.entity.LastProductEntity
import woowacourse.shopping.data.entity.ProductEntity

@Database(
    version = 1,
    entities = [
        ProductEntity::class,
        CartEntity::class,
        LastProductEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
    abstract fun lastProductDao(): LastProductDao
}