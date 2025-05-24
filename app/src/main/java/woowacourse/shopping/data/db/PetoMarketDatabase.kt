package woowacourse.shopping.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.shopping.data.db.dao.CartDao
import woowacourse.shopping.data.db.dao.HistoryDao
import woowacourse.shopping.data.db.entity.CartEntity
import woowacourse.shopping.data.db.entity.HistoryEntity

@Database(
    entities = [CartEntity::class, HistoryEntity::class],
    version = 2,
)
abstract class PetoMarketDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    abstract fun historyDao(): HistoryDao

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
