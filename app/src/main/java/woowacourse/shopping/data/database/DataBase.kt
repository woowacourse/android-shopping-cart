package woowacourse.shopping.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import woowacourse.shopping.data.dao.PurchaseProductsDao
import woowacourse.shopping.data.dao.RecentlyViewedProductDao
import woowacourse.shopping.data.entity.PurchaseProductEntity
import woowacourse.shopping.data.entity.RecentlyViewedProductEntity

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
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
            """.trimIndent()
        )
    }
}

@Database(
    entities = [PurchaseProductEntity::class, RecentlyViewedProductEntity::class],
    version = 3
)
abstract class DataBase: RoomDatabase() {
    abstract fun purchaseProductsDao(): PurchaseProductsDao
    abstract fun recentlyViewedProductDao(): RecentlyViewedProductDao

    companion object {
        @Volatile
        private var instance: DataBase? = null

        fun getDatabase(context: Context): DataBase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context = context,
                    klass = DataBase::class.java,
                    name = "shopping_database"
                ).addMigrations(MIGRATION_2_3).build().also { instance = it }
            }
        }
    }
}