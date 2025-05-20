package woowacourse.shopping.data.repository

import woowacourse.shopping.data.DummyShoppingCart
import woowacourse.shopping.data.ext.subList
import woowacourse.shopping.data.page.Page
import woowacourse.shopping.data.page.PageRequest
import woowacourse.shopping.domain.Product

class ShoppingCartRepositoryImpl : ShoppingCartRepository {
    override fun findAll(pageRequest: PageRequest): Page<Product> {
        val items =
            DummyShoppingCart.products
                .distinct()
                .subList(pageRequest)

        return pageRequest.toPage(items, totalSize())
    }

    override fun totalSize(): Int = DummyShoppingCart.products.size

    override fun remove(product: Product) {
        DummyShoppingCart.products.remove(product)
    }
}
