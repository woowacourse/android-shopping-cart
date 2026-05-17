package woowacourse.shopping.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import woowacourse.shopping.data.local.cart.CartItemDao
import woowacourse.shopping.data.local.cart.CartItemEntity
import woowacourse.shopping.data.local.recent.RecentProductDao
import woowacourse.shopping.data.local.recent.RecentProductEntity

@Database(
    entities = [RecentProductEntity::class, CartItemEntity::class],
    version = 4,
    exportSchema = false,
)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun cartItemDao(): CartItemDao

    abstract fun recentProductDao(): RecentProductDao

    companion object {
        val MIGRATION_3_4: Migration =
            object : Migration(3, 4) {
                override fun migrate(db: SupportSQLiteDatabase) {
                    db.execSQL(
                        """
                        CREATE TABLE IF NOT EXISTS `cart_items_new` (
                            `productId` INTEGER NOT NULL,
                            `name` TEXT NOT NULL,
                            `price` INTEGER NOT NULL,
                            `imageUrl` TEXT NOT NULL,
                            `quantity` INTEGER NOT NULL,
                            PRIMARY KEY(`productId`)
                        )
                        """.trimIndent(),
                    )
                    db.execSQL(
                        """
                        INSERT INTO `cart_items_new` (`productId`, `name`, `price`, `imageUrl`, `quantity`)
                        SELECT CAST(`productId` AS INTEGER), `name`, `price`, `imageUrl`, `quantity`
                        FROM `cart_items`
                        """.trimIndent(),
                    )
                    db.execSQL("DROP TABLE `cart_items`")
                    db.execSQL("ALTER TABLE `cart_items_new` RENAME TO `cart_items`")
                }
            }
    }
}
