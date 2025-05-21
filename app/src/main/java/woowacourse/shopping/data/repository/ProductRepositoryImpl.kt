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
    ): List<Product> {
        val fromIndex = page * pageSize
        val toIndex = minOf(fromIndex + pageSize, DummyProducts.values.size)
        if (fromIndex >= DummyProducts.values.size) return emptyList()
        return DummyProducts.values.subList(fromIndex, toIndex)
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
                }.onFailure { e ->
                    onResult(Result.failure(e))
                }
        }
    }

    override fun insertProduct(
        product: Product,
        onResult: (Result<Unit>) -> Unit,
    ) {
        cartDataSource.insertProduct(product) { result ->
            result
                .onSuccess {
                    onResult(Result.success(Unit))
                }.onFailure { e ->
                    onResult(Result.failure(e))
                }
        }
    }

    override fun deleteProduct(
        productId: Long,
        onResult: (Result<Long>) -> Unit,
    ) {
        cartDataSource.deleteProduct(productId) { result ->
            result
                .onSuccess { id ->
                    onResult(Result.success(id))
                }.onFailure { e ->
                    onResult(Result.failure(e))
                }
        }
    }
}
