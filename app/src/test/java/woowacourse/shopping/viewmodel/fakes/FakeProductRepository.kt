package woowacourse.shopping.viewmodel.fakes

import woowacourse.shopping.data.remote.repository.ProductRepository
import woowacourse.shopping.domain.Product

class FakeProductRepository : ProductRepository {
    private val products = mutableListOf<Product>()

    fun setProducts(newProducts: List<Product>) {
        products.clear()
        products.addAll(newProducts)
    }

    override suspend fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product> = products

    override suspend fun getProduct(id: String): Product = products.first { it.id == id }
}
