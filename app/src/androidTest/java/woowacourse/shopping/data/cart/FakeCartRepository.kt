package woowacourse.shopping.data.cart

import woowacourse.shopping.domain.entity.Cart
import woowacourse.shopping.domain.entity.CartProduct
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.fixtures.fakeProduct

class FakeCartRepository(
    private var cart: Cart = Cart(),
) : CartRepository {
    private val products: List<CartProduct> get() = cart.cartProducts()

    override fun cartProducts(
        currentPage: Int,
        productSize: Int,
    ): Result<List<CartProduct>> {
        if (canLoadMoreCartProducts(currentPage, productSize).getOrThrow()
                .not()
        ) {
            return Result.success(emptyList())
        }
        val startIndex = (currentPage - 1) * productSize
        val endIndex = (startIndex + productSize).coerceAtMost(products.size)

        if (startIndex >= products.size) {
            return Result.failure(
                IllegalArgumentException("Invalid page"),
            )
        }
        return Result.success(products.subList(startIndex, endIndex))
    }

    override fun filterCartProducts(ids: List<Long>): Result<List<CartProduct>> {
        return Result.success(products.filter { it.product.id in ids })
    }

    override fun updateCartProduct(
        productId: Long,
        count: Int,
    ): Result<Long> {
        cart = cart.add(fakeProduct(id = productId, name = "오둥이 $productId"))
        return Result.success(productId)
    }

    override fun deleteCartProduct(productId: Long): Result<Long> {
        cart = cart.remove(fakeProduct(id = productId, name = "오둥이 $productId"))
        return Result.success(productId)
    }

    override fun canLoadMoreCartProducts(
        currentPage: Int,
        pageSize: Int,
    ): Result<Boolean> {
        if (currentPage < 1) return Result.success(false)
        val startIndex = (currentPage - 1) * pageSize
        return Result.success(startIndex < products.size)
    }
}
