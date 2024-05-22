package woowacourse.shopping.data.repository

import woowacourse.shopping.data.db.shopping.DummyShopping
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.view.shopping.ShoppingViewModel

class ShoppingRepositoryImpl : ShoppingRepository {
    private val products: List<Product> = DummyShopping.items
    private var offset = 0

    override fun findProductsByPage(): List<Product> {
        val fromIndex = offset
        offset = Integer.min(offset + ShoppingViewModel.PAGE_SIZE, products.size)
        return products.subList(fromIndex, offset)
    }

    override fun findProductById(id: Long): Product {
        return products.first { it.id == id }
    }

    override fun canLoadMore(): Boolean {
        return !(products.size < ShoppingViewModel.PAGE_SIZE || offset == products.size)
    }
}
