package woowacourse.shopping.data.cartRepository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CartEntity::class], version = 1)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        private const val DB_NAME = "cart"
        private var instance: CartDatabase? = null

        fun initialize(context: Context): CartDatabase =
            instance ?: Room
                .databaseBuilder(
                    context,
                    CartDatabase::class.java,
                    DB_NAME,
                ).build()
                .also { instance = it }
    }
}
