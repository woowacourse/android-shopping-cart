package woowacourse.shopping.ui.viewmodel

import woowacourse.shopping.data.dummyProducts
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.model.Product

class FakeCartRepository : CartRepository {
    var storage = dummyProducts.take(10).toMutableList()

    override fun fetchCartProducts(page: Int, callback: (List<CartProduct>) -> Unit) {
        val pageSize = 5
        val start = (page - 1) * pageSize
        callback(storage.drop(start).take(pageSize).map{CartProduct(it,1)})
    }

    override fun fetchAllProduct(callback: (List<CartProduct>) -> Unit) {
        callback(storage.map{CartProduct(it,1)})
    }

    override fun fetchMaxPageCount(callback: (Int) -> Unit) {
        val pageSize = 5
        callback((storage.size + pageSize - 1) / pageSize)
    }

    override fun upsertCartProduct(product: Product, count: Int) {
        storage.add(product)
    }

    override fun removeCartProduct(id: Int) {
        storage.removeIf { it.id == id }
    }

    override fun clearCart() {
        storage.clear()
    }
}
