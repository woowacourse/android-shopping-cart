package woowacourse.shopping.data.repository

import woowacourse.shopping.data.CartMapper.toEntity
import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.data.db.CartEntity
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import kotlin.concurrent.thread

class CartRepositoryImpl(
    private val cartDataSource: CartDataSource,
) : CartRepository {
    override fun getCartItemCount(onResult: (Result<Int?>) -> Unit) {
        runThread(
            block = { cartDataSource.getCartProductCount() },
            onResult = onResult,
        )
    }

    override fun getTotalQuantity(onResult: (Result<Int?>) -> Unit) {
        runThread(
            block = { cartDataSource.getTotalQuantity() },
            onResult = onResult,
        )
    }

    override fun getCartItemById(
        productId: Long,
        onResult: (Result<CartEntity?>) -> Unit,
    ) {
        runThread(
            block = { cartDataSource.getCartItemById(productId) },
            onResult = onResult,
        )
    }

    override fun insertProduct(
        cartItem: CartItem,
        onResult: (Result<Unit>) -> Unit,
    ) {
        runThread(
            block = { cartDataSource.insertProduct(cartItem.toEntity()) },
            onResult = onResult,
        )
    }

    override fun insertOrIncrease(
        product: Product,
        quantity: Int,
        onResult: (Result<Unit>) -> Unit,
    ) {
        if (cartDataSource.existsByProductId(product.productId)) {
            runThread(
                block = { cartDataSource.increaseQuantity(product.productId, quantity) },
                onResult = onResult,
            )
        } else {
            runThread(
                block = { cartDataSource.insertProduct(product.toEntity(quantity)) },
                onResult = onResult,
            )
        }
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

    private inline fun <T> runThread(
        crossinline block: () -> T,
        crossinline onResult: (Result<T>) -> Unit,
    ) {
        thread {
            onResult(runCatching { block() })
        }
    }
}
