package woowacourse.shopping.model.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [OrderEntity::class], version = 1)
abstract class OrderDatabase : RoomDatabase() {
    abstract fun orderDao(): OrderDao

    companion object {
        @Volatile
        private var databaseInstance: OrderDatabase? = null

        fun getDatabase(context: Context): OrderDatabase {
            val instance =
                Room.databaseBuilder(
                    context.applicationContext,
                    OrderDatabase::class.java,
                    "alsong.db",
                ).build()
            return databaseInstance ?: synchronized(this) { instance }
        }
    }
}
