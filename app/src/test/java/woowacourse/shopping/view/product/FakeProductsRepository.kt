package woowacourse.shopping.view.product

import woowacourse.shopping.data.product.repository.ProductsRepository
import woowacourse.shopping.domain.product.Product

class FakeProductsRepository : ProductsRepository {
    private val products: List<Product> =
        List(50) { Product(id = it.toLong(), name = "럭키", price = 4000) }

    private var recentViewedProduct: List<Product> =
        listOf(
            Product(id = 0, name = "럭키", price = 4000),
            Product(id = 1, name = "럭키", price = 4000),
            Product(id = 2, name = "럭키", price = 4000),
            Product(id = 3, name = "럭키", price = 4000),
        )

    override fun load(onLoad: (Result<List<Product>>) -> Unit) {
        onLoad(runCatching { products })
    }

    override fun loadLatestViewedProduct(onLoad: (Result<Product?>) -> Unit) {
        onLoad(runCatching { products.first() })
    }

    override fun loadLastViewedProducts(onLoad: (Result<List<Product>>) -> Unit) {
        onLoad(runCatching { recentViewedProduct })
    }

    override fun recordViewedProduct(product: Product) {
        recentViewedProduct += product
    }
}
