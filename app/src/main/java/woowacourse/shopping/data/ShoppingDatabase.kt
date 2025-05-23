package woowacourse.shopping.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import woowacourse.shopping.BuildConfig
import woowacourse.shopping.data.carts.CartDao
import woowacourse.shopping.data.carts.CartEntity
import woowacourse.shopping.data.goods.RecentSeenGoodsDao
import woowacourse.shopping.data.goods.RecentSeenGoodsEntity

@Database(entities = [CartEntity::class, RecentSeenGoodsEntity::class], version = 3)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    abstract fun recentSeenGoodsDao(): RecentSeenGoodsDao

    companion object {
        private val MIGRATION_1_2 =
            object : Migration(1, 2) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("ALTER TABLE cart ADD COLUMN quantity INTEGER NOT NULL DEFAULT 1")
                }
            }

        private val MIGRATION_2_3 =
            object : Migration(2, 3) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL(
                        "CREATE TABLE recent_seen_goods (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "goods_id TEXT NOT NULL)",
                    )
                }
            }

        @Volatile
        private var instance: ShoppingDatabase? = null

        fun getDatabase(context: Context): ShoppingDatabase =
            instance ?: synchronized(this) {
                Room
                    .databaseBuilder(
                        context.applicationContext,
                        ShoppingDatabase::class.java,
                        BuildConfig.DB_NAME,
                    ).addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build()
                    .also { instance = it }
            }
    }
}
