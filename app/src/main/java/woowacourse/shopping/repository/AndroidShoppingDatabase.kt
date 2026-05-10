package woowacourse.shopping.repository

import androidx.room.Database
import androidx.room.migration.Migration
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import woowacourse.shopping.repository.dao.ShoppingCartItemDao
import woowacourse.shopping.repository.dao.ViewedProductDao
import woowacourse.shopping.repository.entity.ShoppingCartItemEntity
import woowacourse.shopping.repository.entity.ViewedProductEntity

@Database(entities = [ShoppingCartItemEntity::class, ViewedProductEntity::class], version = 2)
abstract class AndroidShoppingDatabase : RoomDatabase() {
    abstract fun shoppingCartItemDao(): ShoppingCartItemDao

    abstract fun viewedProductDao(): ViewedProductDao

    companion object {
        val MIGRATION_1_2 =
            object : Migration(1, 2) {
                override fun migrate(db: SupportSQLiteDatabase) {
                    db.execSQL(
                        """
                        CREATE TABLE IF NOT EXISTS viewed_products (
                            product_id TEXT NOT NULL PRIMARY KEY,
                            viewed_at INTEGER NOT NULL
                        )
                        """.trimIndent(),
                    )
                }
            }
    }
}
