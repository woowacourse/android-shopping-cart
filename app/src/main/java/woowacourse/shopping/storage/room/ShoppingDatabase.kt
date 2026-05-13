package woowacourse.shopping.storage.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import woowacourse.shopping.storage.room.shoppingItem.ShoppingItemDao
import woowacourse.shopping.storage.room.shoppingItem.ShoppingItemEntity
import woowacourse.shopping.storage.room.shoppingcart.ShoppingCartDao
import woowacourse.shopping.storage.room.shoppingcart.ShoppingCartEntity

@Database(
    entities = [ShoppingItemEntity::class, ShoppingCartEntity::class],
    version = 3,
    exportSchema = false,
)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun shoppingItemDao(): ShoppingItemDao

    abstract fun shoppingCartDao(): ShoppingCartDao

    companion object {
        private const val DATABASE_NAME = "shopping.db"
        private val migration1To3 =
            object : Migration(1, 3) {
                override fun migrate(db: SupportSQLiteDatabase) {
                    // v1 and v3 schemas are identical for Room-managed tables.
                }
            }
        private val migration2To3 =
            object : Migration(2, 3) {
                override fun migrate(db: SupportSQLiteDatabase) {
                    db.execSQL("DROP TABLE IF EXISTS `visited_products`")
                }
            }

        fun create(context: Context): ShoppingDatabase =
            Room
                .databaseBuilder(context, ShoppingDatabase::class.java, DATABASE_NAME)
                .addMigrations(migration1To3, migration2To3)
                .build()
    }
}
