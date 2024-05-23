package woowacourse.shopping.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [CartProductEntity::class], version = 1, exportSchema = false)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun cartProductDao(): CartProductDao

    companion object {
        @Volatile
        private var instance: ShoppingDatabase? = null

        fun getInstance(): ShoppingDatabase =
            instance ?: throw IllegalStateException("ShoppingDatabase must be initialized")

        fun init(context: Context) {
            instance = Room
                .databaseBuilder(context, ShoppingDatabase::class.java, "shopping_database")
                .build()
        }
    }
}
