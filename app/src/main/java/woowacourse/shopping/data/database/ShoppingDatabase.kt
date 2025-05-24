package woowacourse.shopping.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.shopping.data.dao.CartDao
import woowacourse.shopping.data.dao.HistoryDao
import woowacourse.shopping.data.dao.ProductDao
import woowacourse.shopping.data.entity.CartProductEntity
import woowacourse.shopping.data.entity.HistoryProductEntity
import woowacourse.shopping.data.entity.ProductEntity

@Database(
    entities = [ProductEntity::class, CartProductEntity::class, HistoryProductEntity::class],
    version = 10001,
)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    abstract fun cartDao(): CartDao

    abstract fun historyDao(): HistoryDao

    @Suppress("ktlint:standard:property-naming")
    companion object {
        private const val DATABASE_NAME = "SHOPPING_DATABASE"
        private var INSTANCE: ShoppingDatabase? = null
        private val LOCK = Any()

        fun getInstance(context: Context): ShoppingDatabase =
            INSTANCE ?: synchronized(LOCK) {
                INSTANCE ?: Room
                    .databaseBuilder(
                        context.applicationContext,
                        ShoppingDatabase::class.java,
                        DATABASE_NAME,
                    ).createFromAsset("database/shopping_products.db")
                    .build()
                    .also { INSTANCE = it }
            }
    }
}
