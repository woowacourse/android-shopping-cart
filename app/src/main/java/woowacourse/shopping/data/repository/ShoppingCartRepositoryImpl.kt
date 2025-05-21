package woowacourse.shopping.data.repository

import woowacourse.shopping.data.DummyShoppingCart
import woowacourse.shopping.data.ext.subList
import woowacourse.shopping.data.page.Page
import woowacourse.shopping.data.page.PageRequest
import woowacourse.shopping.domain.ShoppingCartItem

class ShoppingCartRepositoryImpl : ShoppingCartRepository {
    override fun findAll(pageRequest: PageRequest): Page<ShoppingCartItem> {
        val items =
            DummyShoppingCart.items
                .distinct()
                .subList(pageRequest)

        return pageRequest.toPage(items, totalSize())
    }

    override fun totalSize(): Int = DummyShoppingCart.items.size

    override fun remove(item: ShoppingCartItem) {
        DummyShoppingCart.items.remove(item)
    }

    override fun save(item: ShoppingCartItem) {
        DummyShoppingCart.items.add(item)
    }
}
