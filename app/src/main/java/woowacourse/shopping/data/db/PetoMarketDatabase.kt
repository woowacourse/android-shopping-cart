package woowacourse.shopping.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.shopping.data.db.dao.CartDao
import woowacourse.shopping.data.db.entity.CartEntity

@Database(
    entities = [CartEntity::class],
    version = 1,
)
abstract class PetoMarketDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var instance: PetoMarketDatabase? = null

        fun getInstance(context: Context): PetoMarketDatabase {
            return instance ?: synchronized(this) {
                val newInstance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        PetoMarketDatabase::class.java,
                        "peto_market_database",
                    ).build()
                instance = newInstance
                newInstance
            }
        }
    }
}
