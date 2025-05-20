package woowacourse.shopping.fixture

import woowacourse.shopping.data.DummyShoppingCart
import woowacourse.shopping.data.ext.subList
import woowacourse.shopping.data.page.Page
import woowacourse.shopping.data.page.PageRequest
import woowacourse.shopping.data.repository.ShoppingCartRepository
import woowacourse.shopping.domain.Product

class FakeShoppingCartRepository(
    private val data: MutableList<Product> = DummyShoppingCart.products.toMutableList(),
) : ShoppingCartRepository {
    override fun findAll(pageRequest: PageRequest): Page<Product> {
        val items =
            data
                .distinct()
                .subList(pageRequest)

        return pageRequest.toPage(items, totalSize())
    }

    override fun remove(product: Product) {
        data.remove(product)
    }

    override fun totalSize(): Int {
        return TestShoppingCart.products.size
    }
}
