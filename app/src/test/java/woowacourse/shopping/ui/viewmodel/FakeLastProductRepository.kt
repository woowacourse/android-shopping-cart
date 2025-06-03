package woowacourse.shopping.ui.viewmodel

import woowacourse.shopping.data.repository.LastProductRepository
import woowacourse.shopping.domain.model.LastProduct
import woowacourse.shopping.domain.model.Product

class FakeLastProductRepository:LastProductRepository {
    override fun fetchProducts(callback: (List<LastProduct>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun insertProduct(product: Product) {
        TODO("Not yet implemented")
    }

    override fun deleteLastProduct() {
        TODO("Not yet implemented")
    }

    override fun fetchLatestProduct(callback: (Product?) -> Unit) {
        TODO("Not yet implemented")
    }
}