package woowacourse.shopping.data.cart

import woowacourse.shopping.data.shopping.product.ProductDataSource
import woowacourse.shopping.domain.entity.CartProduct
import woowacourse.shopping.domain.repository.CartRepository

class DefaultCartRepository(
    private val cartDataSource: CartDataSource,
    private val productDataSource: ProductDataSource,
) : CartRepository {
    override fun cartProducts(
        currentPage: Int,
        pageSize: Int,
    ): Result<List<CartProduct>> {
        return cartDataSource.loadCarts(currentPage, pageSize).mapCatching { cartProducts ->
            cartProducts.map { (id, count) ->
                productDataSource.productById(id).getOrThrow().let { product ->
                    CartProduct(product, count)
                }
            }
        }
    }

    override fun addCartProduct(
        productId: Long,
        count: Int,
    ): Result<Long> {
        productDataSource.productById(productId).onSuccess {
            return cartDataSource.addCartProduct(CartProduct(it, count))
        }
        return Result.failure(IllegalArgumentException("Product(id=$productId) not found."))
    }

    override fun deleteCartProduct(productId: Long): Result<Long> {
        productDataSource.productById(productId).onSuccess {
            return cartDataSource.deleteCartProduct(productId)
        }
        return Result.failure(IllegalArgumentException("Product(id=$productId) not found."))
    }

    override fun canLoadMoreCartProducts(
        currentPage: Int,
        pageSize: Int,
    ): Result<Boolean> {
        if (currentPage < 1) return Result.success(false)
        val minSize = (currentPage - 1) * pageSize
        return cartDataSource.canLoadMoreCart(minSize)
    }
}
