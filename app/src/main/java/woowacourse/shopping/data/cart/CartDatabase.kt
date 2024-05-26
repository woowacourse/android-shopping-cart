package woowacourse.shopping.data.cart

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.shopping.data.Converters

@Database(entities = [CartItemEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        fun initialize(context: Context) =
            Room.databaseBuilder(
                context,
                CartDatabase::class.java,
                "cart",
            ).build()
    }
}
