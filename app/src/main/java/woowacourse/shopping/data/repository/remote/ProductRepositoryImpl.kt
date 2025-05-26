package woowacourse.shopping.data.repository.remote

import woowacourse.shopping.data.datasource.local.CartDataSource
import woowacourse.shopping.data.datasource.remote.ProductDataSource
import woowacourse.shopping.data.runThread
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val cartDataSource: CartDataSource,
    private val productDataSource: ProductDataSource,
) : ProductRepository {
    override fun fetchPagingProducts(
        page: Int,
        pageSize: Int,
        onResult: (Result<List<CartItem>>) -> Unit,
    ) {
        runThread(
            block = {
                productDataSource.fetchPagingProducts(page, pageSize).toCartItems()
            },
            onResult = onResult,
        )
    }

    override fun fetchProductById(
        productId: Long,
        onResult: (Result<Product>) -> Unit,
    ) {
        runThread(
            block = { productDataSource.fetchProductById(productId) },
            onResult = onResult,
        )
    }

    private fun List<Product>.toCartItems(): List<CartItem> =
        this.map { product ->
            val quantity = cartDataSource.getQuantityById(product.productId)
            CartItem(product, quantity)
        }
}
