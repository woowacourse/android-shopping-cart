package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.data.db.CartEntity
import woowacourse.shopping.data.runThread
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Price
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val cartDataSource: CartDataSource,
    private val productDataSource: ProductDataSource,
) : ProductRepository {
    override fun start(onResult: (Result<Unit>) -> Unit) {
        runThread(
            block = { productDataSource.start() },
            onResult = onResult,
        )
    }

    override fun fetchPagingProducts(
        page: Int,
        pageSize: Int,
        onResult: (Result<List<CartItem>>) -> Unit,
    ) {
        runThread(
            block = { productDataSource.fetchPagingProducts(page, pageSize).toCartItems() },
            onResult = onResult,
        )
    }

    override fun fetchProducts(onResult: (Result<List<Product>>) -> Unit) {
        runThread(
            block = { productDataSource.fetchProducts() },
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

    override fun fetchCartItems(onResult: (Result<List<CartItem>>) -> Unit) {
        runThread(
            block = { cartDataSource.getCartProducts().map { it.toCartItem() } },
            onResult = onResult,
        )
    }

    override fun fetchPagedCartItems(
        page: Int,
        pageSize: Int,
        onResult: (Result<List<CartItem>>) -> Unit,
    ) {
        runThread(
            block = { cartDataSource.getPagedCartProducts(page, pageSize).map { it.toCartItem() } },
            onResult = onResult,
        )
    }

    override fun shutdown(onResult: (Result<Unit>) -> Unit) {
        runThread(
            block = { productDataSource.shutdown() },
            onResult = onResult,
        )
    }

    private fun CartEntity.toCartItem(): CartItem = CartItem(Product(0, "1", Price(1), ""), 10)

    private fun List<Product>.toCartItems(): List<CartItem> =
        this.map { product ->
            val quantity = cartDataSource.getQuantityById(product.productId)
            CartItem(product, quantity)
        }
}
