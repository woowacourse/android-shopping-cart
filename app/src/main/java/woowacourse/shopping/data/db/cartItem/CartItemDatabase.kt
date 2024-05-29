package woowacourse.shopping.data.db.cartItem

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import woowacourse.shopping.data.model.CartItemEntity

@Database(
    entities = [
        CartItemEntity::class,
    ],
    version = 3,
)
@TypeConverters(CartItemConverters::class)
abstract class CartItemDatabase : RoomDatabase() {
    abstract fun cartItemDao(): CartItemDao

    companion object {
        private var instance: CartItemDatabase? = null
        const val CART_ITEMS_DB_NAME = "cartItems"

        private val MIGRATION_2_3 =
            object : Migration(2, 3) {
                override fun migrate(db: SupportSQLiteDatabase) {
                    db.execSQL("ALTER TABLE $CART_ITEMS_DB_NAME ADD COLUMN productId INTEGER NOT NULL DEFAULT 0")
                }
            }

        @Synchronized
        fun getInstance(context: Context): CartItemDatabase {
            return instance
                ?: synchronized(CartItemDatabase::class) {
                    Room.databaseBuilder(
                        context.applicationContext,
                        CartItemDatabase::class.java,
                        CART_ITEMS_DB_NAME,
                    ).addMigrations(MIGRATION_2_3).build()
                }
        }
    }
}
