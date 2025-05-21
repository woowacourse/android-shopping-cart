package woowacourse.shopping.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import woowacourse.shopping.BuildConfig

@Database(entities = [CartEntity::class], version = 2)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        private val MIGRATION_1_2 =
            object : Migration(1, 2) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("ALTER TABLE cart ADD COLUMN quantity INTEGER NOT NULL DEFAULT 1")
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
                    ).addMigrations(MIGRATION_1_2)
                    .build()
                    .also { instance = it }
            }
    }
}
