package woowacourse.shopping.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.shopping.BuildConfig

@Database(entities = [CartEntity::class], version = 1)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var instance: ShoppingDatabase? = null

        fun getDatabase(context: Context): ShoppingDatabase =
            instance ?: synchronized(this) {
                Room
                    .databaseBuilder(
                        context.applicationContext,
                        ShoppingDatabase::class.java,
                        BuildConfig.DB_NAME,
                    ).build()
                    .also { instance = it }
            }
    }
}
