package woowacourse.shopping.local.history

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.shopping.data.model.HistoryProduct

@Database(entities = [HistoryProduct::class], version = 1)
abstract class HistoryProductDatabase : RoomDatabase() {
    abstract fun dao(): HistoryProductDao

    companion object {
        @Volatile
        private var instance: HistoryProductDatabase? = null

        fun database(context: Context): HistoryProductDatabase =
            instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context, HistoryProductDatabase::class.java, "history_product_database",
                ).build().also { instance = it }
            }
    }
}
