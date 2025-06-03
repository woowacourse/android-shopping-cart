package woowacourse.shopping.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.shopping.data.dao.CartProductDao
import woowacourse.shopping.data.dao.RecentProductDao
import woowacourse.shopping.data.entity.CartProductEntity
import woowacourse.shopping.data.entity.RecentProductEntity

@Database(
    entities = [CartProductEntity::class, RecentProductEntity::class],
    version = 1,
)
abstract class ShoppingCartDatabase : RoomDatabase() {
    abstract val cartProductDao: CartProductDao
    abstract val recentProductDao: RecentProductDao

    companion object {
        private const val DATABASE_NAME = "shopping-cart-db"

        @Volatile
        private var shoppingCartDatabase: ShoppingCartDatabase? = null

        fun getDataBase(context: Context): ShoppingCartDatabase =
            shoppingCartDatabase ?: synchronized(this) {
                val instance =
                    Room
                        .databaseBuilder(
                            context.applicationContext,
                            ShoppingCartDatabase::class.java,
                            DATABASE_NAME,
                        ).build()
                shoppingCartDatabase = instance
                instance
            }
    }
}
