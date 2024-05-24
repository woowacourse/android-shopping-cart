package woowacourse.shopping.data.cart

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Cart::class], version = 1)
@TypeConverters(CartTypeConverters::class)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        private const val DATABASE_NAME = "cart_db"
        private const val ERROR_DATABASE = "데이터베이스가 초기화 되지 않았습니다."
        private var database: CartDatabase? = null

        fun database(): CartDatabase {
            return database ?: throw IllegalStateException(ERROR_DATABASE)
        }

        fun initialize(context: Context) {
            if (database == null) {
                database =
                    Room.databaseBuilder(
                        context.applicationContext,
                        CartDatabase::class.java,
                        DATABASE_NAME,
                    ).build()
            }
        }
    }
}
