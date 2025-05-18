package woowacourse.shopping.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.shopping.data.dao.ProductDao
import woowacourse.shopping.data.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

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
                    ).build()
                    .also { INSTANCE = it }
            }
    }
}
