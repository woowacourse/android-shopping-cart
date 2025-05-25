package woowacourse.shopping.data.product.database

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.data.product.dao.ProductDao
import woowacourse.shopping.data.product.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun dao(): ProductDao
}
