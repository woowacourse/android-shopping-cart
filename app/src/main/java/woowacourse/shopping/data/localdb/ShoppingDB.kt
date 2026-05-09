package woowacourse.shopping.data.localdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.shopping.data.localdb.dao.CartItemDao
import woowacourse.shopping.data.localdb.dao.RecentItemDao
import woowacourse.shopping.data.localdb.entity.CartItemEntity
import woowacourse.shopping.data.localdb.entity.RecentItemEntity
import kotlin.jvm.java

@Database(
    entities = [CartItemEntity::class, RecentItemEntity::class],
    version = 1,
)
abstract class ShoppingDB : RoomDatabase() {
    abstract fun cartItemDao(): CartItemDao

    abstract fun recentItemDao(): RecentItemDao

    companion object {
        @Volatile
        private var instance: ShoppingDB? = null

        fun getInstance(context: Context): ShoppingDB =
            instance ?: synchronized(this) {
                instance ?: Room
                    .databaseBuilder(
                        context.applicationContext,
                        ShoppingDB::class.java,
                        "shopping_db",
                    ).build()
                    .also {
                        instance = it
                    }
            }
    }
}
