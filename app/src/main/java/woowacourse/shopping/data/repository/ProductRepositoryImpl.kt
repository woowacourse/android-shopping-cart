package woowacourse.shopping.data.repository

import woowacourse.shopping.data.DummyProducts
import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val cartDataSource: CartDataSource,
    private val productDataSource: ProductDataSource,
) : ProductRepository {
    override fun getProducts(): List<Product> = productDataSource.getProducts()

    override fun getPagedProducts(
        page: Int,
        pageSize: Int,
        onResult: (Result<List<CartItem>>) -> Unit,
    ) {
        val fromIndex = page * pageSize
        val toIndex = minOf(fromIndex + pageSize, DummyProducts.values.size)
        if (fromIndex >= DummyProducts.values.size) return onResult(Result.success(emptyList()))

        val productPage = DummyProducts.values.slice(fromIndex until toIndex)
        val resultList = MutableList<CartItem?>(productPage.size) { null }
        var completedCount = 0

        productPage.forEachIndexed { index, product ->
            cartDataSource.getCartItemById(product.productId) { result ->
                result
                    .onSuccess {
                        val quantity = result.getOrNull()?.quantity ?: 0

                        resultList[index] =
                            CartItem(
                                product = product,
                                quantity = quantity,
                            )

                        completedCount++
                        if (completedCount == productPage.size) {
                            onResult(Result.success(resultList.filterNotNull()))
                        }
                    }.onFailure { e -> onResult(Result.failure(e)) }
            }
        }
    }

    override fun getCartItems(onResult: (Result<List<CartItem>>) -> Unit) {
        cartDataSource.getCartProducts { result ->
            result
                .onSuccess { cartEntities ->
                    val cartItems =
                        cartEntities.mapNotNull { entity ->
                            val product = productDataSource.getProductById(entity.productId)
                            product?.let { CartItem(it, entity.quantity) }
                        }
                    onResult(Result.success(cartItems))
                }.onFailure { e -> onResult(Result.failure(e)) }
        }
    }

    override fun getPagedCartItems(
        page: Int,
        pageSize: Int,
        onResult: (Result<List<CartItem>>) -> Unit,
    ) {
        cartDataSource.getPagedCartProducts(page, pageSize) { result ->
            result
                .onSuccess { cartEntities ->
                    val cartItems =
                        cartEntities.mapNotNull { entity ->
                            val product = productDataSource.getProductById(entity.productId)
                            product?.let { CartItem(it, entity.quantity) }
                        }
                    onResult(Result.success(cartItems))
                }.onFailure { e -> onResult(Result.failure(e)) }
        }
    }
}
