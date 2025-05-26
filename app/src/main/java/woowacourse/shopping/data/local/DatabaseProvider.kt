package woowacourse.shopping.data.local

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    fun provideDatabase(context: Context): ShoppingCartDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ShoppingCartDatabase::class.java,
            "cart-db"
        ).build()


    }
}
