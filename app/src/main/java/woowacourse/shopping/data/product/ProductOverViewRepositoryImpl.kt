package woowacourse.shopping.data.product

import woowacourse.shopping.domain.cart.CartDataSource
import woowacourse.shopping.domain.cart.CartProduct
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductDataSource
import woowacourse.shopping.domain.product.ProductOverViewRepository
import woowacourse.shopping.providers.ThreadProvider

class ProductOverViewRepositoryImpl(
    private val productDataSource: ProductDataSource,
    private val cartDataSource: CartDataSource,
) : ProductOverViewRepository {
    override fun findInRange(
        limit: Int,
        offset: Int,
        onResult: (Result<List<CartProduct>>) -> Unit,
    ) {
        ThreadProvider.execute {
            runCatching {
                val productsResult = productDataSource.findInRange(limit, offset)
                val cartProductsResult = productsResult.mapCatching {
                    val products = productsResult.getOrNull() ?: emptyList()
                    products.map { product ->
                        val cartProductResult = cartDataSource.findByProductId(product.id!!)
                        cartProductResult.getOrNull() ?: CartProduct(
                            product = product,
                            _quantity = 0
                        )
                    }
                }
                onResult(cartProductsResult)
            }
        }
    }

    override fun findById(
        productId: Long,
        onResult: (Result<Product?>) -> Unit,
    ) {
        ThreadProvider.execute {
            runCatching {
                onResult(productDataSource.findById(productId))
            }
        }
    }

    override fun insertAll(
        vararg products: Product,
        onResult: (Result<Unit>) -> Unit,
    ) {
        ThreadProvider.execute {
            runCatching {
                onResult(productDataSource.insertAll(*products))
            }
        }
    }

    override fun insertOrAddQuantity(
        productId: Long,
        delta: Int,
        onResult: (Result<Unit>) -> Unit,
    ) {
        ThreadProvider.execute {
            runCatching {
                onResult(cartDataSource.insertOrAddQuantity(productId, delta))
            }
        }
    }

    override fun updateQuantityByProductId(
        productId: Long,
        delta: Int,
        onResult: (Result<Unit>) -> Unit,
    ) {
        ThreadProvider.execute {
            runCatching {
                onResult(cartDataSource.updateQuantityByProductId(productId, delta))
            }
        }
    }

    override fun removeInCart(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) {
        ThreadProvider.execute {
            runCatching {
                onResult(cartDataSource.deleteByProductId(productId))
            }
        }
    }
}
