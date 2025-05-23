package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.data.db.CartEntity
import woowacourse.shopping.data.runThread
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val cartDataSource: CartDataSource,
    private val productDataSource: ProductDataSource,
) : ProductRepository {
    override fun getProducts(): List<Product> = productDataSource.getProducts()

    override fun getProductById(productId: Long): Product = productDataSource.getProductById(productId)

    override fun getPagedProducts(
        page: Int,
        pageSize: Int,
        onResult: (Result<List<CartItem>>) -> Unit,
    ) {
        runThread(
            block = {
                val productPage = productDataSource.getPagedProducts(page, pageSize)
                val cartItems = productPage.toCartItems()
                cartItems
            },
            onResult = onResult,
        )
    }

    override fun getCartItems(onResult: (Result<List<CartItem>>) -> Unit) {
        runThread(
            block = { cartDataSource.getCartProducts().map { it.toCartItem() } },
            onResult = onResult,
        )
    }

    override fun getPagedCartItems(
        page: Int,
        pageSize: Int,
        onResult: (Result<List<CartItem>>) -> Unit,
    ) {
        runThread(
            block = { cartDataSource.getPagedCartProducts(page, pageSize).map { it.toCartItem() } },
            onResult = onResult,
        )
    }

    private fun CartEntity.toCartItem(): CartItem = CartItem(getProductById(productId), quantity)

    private fun List<Product>.toCartItems(): List<CartItem> =
        this.map { product ->
            val quantity = cartDataSource.getQuantityById(product.productId)
            CartItem(product, quantity)
        }
}
