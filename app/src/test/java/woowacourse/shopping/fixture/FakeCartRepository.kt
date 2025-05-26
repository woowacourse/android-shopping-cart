package woowacourse.shopping.fixture

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.repository.CartRepository

class FakeCartRepository : CartRepository {
    override fun getCartItemCount(onResult: (Result<Int?>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getTotalQuantity(onResult: (Result<Int?>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun insertProduct(
        cartItem: CartItem,
        onResult: (Result<Unit>) -> Unit,
    ) {
        onResult(Result.success(Unit))
    }

    override fun insertOrIncrease(
        productId: Long,
        quantity: Int,
        onResult: (Result<Unit>) -> Unit,
    ) {
        onResult(Result.success(Unit))
    }

    override fun increaseQuantity(
        productId: Long,
        quantity: Int,
        onResult: (Result<Unit>) -> Unit,
    ) {
        TODO("Not yet implemented")
    }

    override fun decreaseQuantity(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) {
        TODO("Not yet implemented")
    }

    override fun deleteProduct(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) {
        TODO("Not yet implemented")
    }
}
