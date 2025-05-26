package woowacourse.shopping.data.repository.local

import woowacourse.shopping.data.CartItemMapper
import woowacourse.shopping.data.datasource.local.CartDataSource
import woowacourse.shopping.data.entity.CartEntity
import woowacourse.shopping.data.runThread
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.repository.CartRepository

class CartRepositoryImpl(
    private val cartDataSource: CartDataSource,
    private val cartItemMapper: CartItemMapper,
) : CartRepository {
    override fun getCartItemCount(onResult: (Result<Int?>) -> Unit) {
        runThread(
            block = { cartDataSource.getCartProductCount() },
            onResult = onResult,
        )
    }

    override fun fetchPagedCartItems(
        page: Int,
        pageSize: Int,
        onResult: (Result<List<CartItem>>) -> Unit,
    ) {
        runThread(
            block = {
                val entities = cartDataSource.getPagedCartProducts(page, pageSize)
                entities.map { cartItemMapper.toDomain(it) }
            },
            onResult = onResult,
        )
    }

    override fun getTotalQuantity(onResult: (Result<Int?>) -> Unit) {
        runThread(
            block = { cartDataSource.getTotalQuantity() },
            onResult = onResult,
        )
    }

    override fun insertProduct(
        cartItem: CartItem,
        onResult: (Result<Unit>) -> Unit,
    ) {
        runThread(
            block = { cartDataSource.insertProduct(cartItemMapper.toData(cartItem)) },
            onResult = onResult,
        )
    }

    override fun insertOrIncrease(
        productId: Long,
        quantity: Int,
        onResult: (Result<Unit>) -> Unit,
    ) {
        runThread(
            block = {
                val exists = cartDataSource.existsByProductId(productId)
                if (exists) {
                    cartDataSource.increaseQuantity(productId, quantity)
                } else {
                    cartDataSource.insertProduct(
                        CartEntity(productId = productId, quantity = quantity),
                    )
                }
            },
            onResult = onResult,
        )
    }

    override fun increaseQuantity(
        productId: Long,
        quantity: Int,
        onResult: (Result<Unit>) -> Unit,
    ) {
        runThread(
            block = { cartDataSource.increaseQuantity(productId, quantity) },
            onResult = onResult,
        )
    }

    override fun decreaseQuantity(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) {
        runThread(
            block = { cartDataSource.decreaseQuantity(productId) },
            onResult = onResult,
        )
    }

    override fun deleteProduct(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) {
        runThread(
            block = { cartDataSource.deleteProductById(productId) },
            onResult = onResult,
        )
    }
}
