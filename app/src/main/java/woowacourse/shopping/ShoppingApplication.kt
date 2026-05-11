package woowacourse.shopping

import android.app.Application
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import woowacourse.shopping.data.db.ShoppingDatabase
import woowacourse.shopping.repository.cartRepository.RoomCartRepository

class ShoppingApplication : Application() {
    val database by lazy {
        Room.databaseBuilder(this, ShoppingDatabase::class.java, "shopping.db").build()
    }

    val cartRepository by lazy {
        RoomCartRepository(
            dao = database.shoppingDao(),
            externalScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        )
    }
}
