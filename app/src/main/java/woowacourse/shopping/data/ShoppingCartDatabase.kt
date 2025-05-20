package woowacourse.shopping.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.shopping.data.cart.CartProductDao
import woowacourse.shopping.data.cart.CartProductEntity
import woowacourse.shopping.data.product.ProductEntity

@Database(entities = [ProductEntity::class, CartProductEntity::class], version = 1)
abstract class ShoppingCartDatabase : RoomDatabase() {
    abstract val cartProductDao: CartProductDao

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
