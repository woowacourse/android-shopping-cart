package woowacourse.shopping.data.cart.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.shopping.data.cart.converter.CartItemConverter
import woowacourse.shopping.data.cart.dao.CartDao
import woowacourse.shopping.data.cart.entity.CartItem

@Database(entities = [CartItem::class], version = 1)
@TypeConverters(CartItemConverter::class)
abstract class CartDataBase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var instance: CartDataBase? = null

        fun instance(context: Context): CartDataBase {
            return instance ?: synchronized(this) {
                val newInstance =
                    Room.databaseBuilder(context, CartDataBase::class.java, "cart").build()
                instance = newInstance
                newInstance
            }
        }
    }
}
