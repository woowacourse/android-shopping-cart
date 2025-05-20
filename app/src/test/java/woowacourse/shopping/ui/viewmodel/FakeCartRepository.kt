package woowacourse.shopping.ui.viewmodel

import woowacourse.shopping.data.dummyProducts
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.domain.model.Product

class FakeCartRepository : CartRepository {
    var storage = dummyProducts.take(10).toMutableList()

    override fun fetchCartProducts(page: Int): List<Product> {
        val pageSize = 5
        val start = (page - 1) * pageSize
        return storage.drop(start).take(pageSize)
    }

    override fun fetchMaxPageCount(): Int {
        val pageSize = 5
        return (storage.size + pageSize - 1) / pageSize
    }

    override fun addCartProduct(product: Product) {
        storage.add(product)
    }

    override fun removeCartProduct(id: Int) {
        storage.removeIf { it.id == id }
    }

    override fun clearCart() {
        storage.clear()
    }
}
