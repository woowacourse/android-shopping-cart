package woowacourse.shopping.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import woowacourse.shopping.data.local.dao.PurchaseProductsDao
import woowacourse.shopping.data.local.dao.RecentlyViewedProductDao
import woowacourse.shopping.data.local.entity.PurchaseProductEntity
import woowacourse.shopping.data.local.entity.RecentlyViewedProductEntity

val MIGRATION_3_4 =
    object : Migration(3, 4) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("DROP TABLE `purchase_products`")
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS `purchase_products` (
                    `id` TEXT NOT NULL,
                    `count` INTEGER NOT NULL,
                    PRIMARY KEY(`id`)
                )
                """.trimIndent()
            )
            db.execSQL("DROP TABLE `recently_viewed_products`")
            db.execSQL(
                """                
                CREATE TABLE IF NOT EXISTS `recently_viewed_products` (
                    `id` TEXT NOT NULL,
                    `name` TEXT NOT NULL,
                    `price` INTEGER NOT NULL,
                    `imageUri` TEXT NOT NULL,
                    `time_stamp` INTEGER NOT NULL,
                    PRIMARY KEY(`id`)
                )
                """.trimIndent(),
            )
        }
    }

@Database(
    entities = [PurchaseProductEntity::class, RecentlyViewedProductEntity::class],
    version = 4,
)
abstract class DataBase : RoomDatabase() {
    abstract fun purchaseProductsDao(): PurchaseProductsDao

    abstract fun recentlyViewedProductDao(): RecentlyViewedProductDao

    companion object {
        @Volatile
        private var instance: DataBase? = null

        fun getDatabase(context: Context): DataBase =
            instance ?: synchronized(this) {
                Room
                    .databaseBuilder(
                        context = context,
                        klass = DataBase::class.java,
                        name = "shopping_database",
                    ).addMigrations(MIGRATION_3_4)
                    .build()
                    .also { instance = it }
            }
    }
}
