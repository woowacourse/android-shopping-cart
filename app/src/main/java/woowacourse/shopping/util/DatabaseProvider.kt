package woowacourse.shopping.util

import android.content.Context
import androidx.room.Room
import woowacourse.shopping.data.database.AppDatabase

object DatabaseProvider {
    private var instance: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        if (instance == null) {
            instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "shopping.db"
            ).build()
        }
        return instance!!
    }
}