package woowacourse.shopping

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import woowacourse.shopping.network.AndroidNetworkStatusMonitor
import woowacourse.shopping.network.NetworkStatusMonitor
import woowacourse.shopping.repository.RoomShoppingCartRepository
import woowacourse.shopping.repository.RoomShoppingItemRepository
import woowacourse.shopping.repository.ShoppingCartRepository
import woowacourse.shopping.repository.ShoppingItemRepository
import woowacourse.shopping.storage.datastore.DataStoreVisitStore
import woowacourse.shopping.storage.datastore.VisitStore
import woowacourse.shopping.storage.room.shoppingItem.ShoppingItemDao
import woowacourse.shopping.storage.room.shoppingcart.ShoppingCartDao

class AppContainer(
    context: Context,
    shoppingItemDao: ShoppingItemDao,
    shoppingCartDao: ShoppingCartDao,
    applicationScope: CoroutineScope,
) {
    val shoppingItemRepository: ShoppingItemRepository =
        RoomShoppingItemRepository(
            shoppingItemDao = shoppingItemDao,
            scope = applicationScope,
        )

    val shoppingCartRepository: ShoppingCartRepository =
        RoomShoppingCartRepository(
            shoppingCartDao = shoppingCartDao,
        )

    val visitStore: VisitStore =
        DataStoreVisitStore(
            context = context,
            scope = applicationScope,
        )

    val networkStatusMonitor: NetworkStatusMonitor =
        AndroidNetworkStatusMonitor(
            context = context,
            scope = applicationScope,
        )
}
