package woowacourse.shopping.fixture

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.repository.CartRepository

class FakeCartRepository : CartRepository {
    override fun getCartItemCount(onResult: (Result<Int?>) -> Unit) {
        onResult(Result.success(3))
    }

    override fun fetchPagedCartItems(
        page: Int,
        pageSize: Int,
        onResult: (Result<List<CartItem>>) -> Unit,
    ) {
        onResult(Result.success(ProductsFixture.dummyCartItems))
    }

    override fun getTotalQuantity(onResult: (Result<Int?>) -> Unit) {
        onResult(Result.success(10))
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
        onResult(Result.success(Unit))
    }

    override fun decreaseQuantity(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) {
        onResult(Result.success(Unit))
    }

    override fun deleteProduct(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) {
        onResult(Result.success(Unit))
    }
}
