package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.DummyInventoryRepository
import woowacourse.shopping.data.DummyShoppingCartRepository
import woowacourse.shopping.data.InventoryRepository
import woowacourse.shopping.data.ShoppingCartRepository

class ShoppingApplication : Application() {
    val inventoryRepository: InventoryRepository by lazy { DummyInventoryRepository() }
    val shoppingCartRepository: ShoppingCartRepository by lazy { DummyShoppingCartRepository() }
}
