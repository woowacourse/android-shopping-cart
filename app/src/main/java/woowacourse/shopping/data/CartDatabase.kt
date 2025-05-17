package woowacourse.shopping.data

import woowacourse.shopping.BuildConfig
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CartEntity::class], version = 1)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var instance: CartDatabase? = null

        fun getDatabase(context: Context): CartDatabase =
            instance ?: synchronized(this) {
                Room
                    .databaseBuilder(
                        context.applicationContext,
                        CartDatabase::class.java,
                        BuildConfig.DB_NAME,
                    ).build()
                    .also { instance = it }
            }
    }
}
