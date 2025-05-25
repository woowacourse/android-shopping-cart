package woowacourse.shopping.data.recent

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.shopping.data.cart.CartItem

@Database(entities = [CartItem::class], version = 1)
abstract class ViewedItemDatabase : RoomDatabase() {
    abstract fun viewedItemDao(): ViewedItemDao

    companion object {
        @Volatile
        private var instance: ViewedItemDatabase? = null

        fun getInstance(context: Context): ViewedItemDatabase =
            instance ?: synchronized(this) {
                val newInstance =
                    Room
                        .databaseBuilder(
                            context.applicationContext,
                            ViewedItemDatabase::class.java,
                            "viewedItemDatabase",
                        ).build()
                instance = newInstance
                newInstance
            }
    }
}
