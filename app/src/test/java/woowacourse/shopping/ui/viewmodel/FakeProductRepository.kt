package woowacourse.shopping.ui.viewmodel

import woowacourse.shopping.data.dummyProducts
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.domain.model.Product

class FakeProductRepository : ProductRepository {
    private val all = dummyProducts.toList()

    override fun fetchProducts(count: Int, lastId: Int, callback: (List<Product>) -> Unit) {
        callback(
            all
            .filter { it.id > lastId }
            .take(count))
    }


    override fun fetchProductDetail(id: Int, callback: (Product?) -> Unit) {
        callback(all.find { it.id == id })
    }


    override fun fetchIsProductsLoadable(lastId: Int, callback: (Boolean) -> Unit) {
        callback(all.any { it.id > lastId })
    }

}
