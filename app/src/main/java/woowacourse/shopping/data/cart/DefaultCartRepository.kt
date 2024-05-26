package woowacourse.shopping.data.cart

import woowacourse.shopping.data.cart.model.CartData
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
        return cartDataSource.loadCarts(currentPage, pageSize).toCartProducts()
    }

    override fun filterCartProducts(ids: List<Long>): Result<List<CartProduct>> {
        return cartDataSource.filterCartProducts(ids).toCartProducts()
    }

    override fun updateCartProduct(
        productId: Long,
        count: Int,
    ): Result<Long> {
        if (count < 1) return Result.failure(IllegalArgumentException("Count(=$count) 는 0이상 이여야 합니다."))
        val productResult = productDataSource.productById(productId)
        if (productResult.isFailure) {
            return Result.failure(NoSuchElementException("Product(id=$productId) not found."))
        }
        return cartDataSource.addCartProduct(CartProduct(productResult.getOrThrow(), count))
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

    private fun Result<List<CartData>>.toCartProducts(): Result<List<CartProduct>> {
        return mapCatching { cartDatas ->
            cartDatas.map { cartData ->
                val result = productDataSource.productById(cartData.id)
                if (result.isFailure) throw NoSuchElementException("Product(id=${cartData.id}) not found.")
                CartProduct(result.getOrThrow(), cartData.count)
            }
        }
    }
}
