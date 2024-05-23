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

    override fun filterCartProducts(ids: List<Long>): Result<List<CartProduct>> {
        val result = cartDataSource.filterCartProducts(ids)
        return result.mapCatching {
            it.map { cartData ->
                val productResult = productDataSource.productById(cartData.id)
                if (productResult.isFailure) error("Product(id=${cartData.id}) not found")
                CartProduct(productResult.getOrThrow(), cartData.count)
            }
        }
    }

    override fun updateCartProduct(
        productId: Long,
        count: Int,
    ): Result<Long> {
        if (count < 1) return Result.failure(IllegalArgumentException("Count(=$count) 는 0이상 이여야 합니다."))
        val productResult = productDataSource.productById(productId)
        if (productResult.isSuccess) {
            val r = cartDataSource.addCartProduct(CartProduct(productResult.getOrThrow(), count))
            return r
        }
        return Result.failure(IllegalArgumentException("Product(id=$productId) not found."))
    }

    override fun deleteCartProduct(productId: Long): Result<Long> {
        return cartDataSource.deleteCartProduct(productId)
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
