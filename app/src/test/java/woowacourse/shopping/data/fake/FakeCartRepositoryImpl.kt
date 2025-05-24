package woowacourse.shopping.data.fake

import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.data.db.entity.CartEntity
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.cart.CartSinglePage
import woowacourse.shopping.domain.repository.CartRepository

class FakeCartRepositoryImpl(
    private val dataSource: CartDataSource,
) : CartRepository {
    override fun getCart(
        id: Long,
        onResult: (cart: Cart?) -> Unit,
    ) {
        val result = dataSource.getCartByProductId(id)?.toDomain()
        onResult(result)
    }

    override fun getCarts(
        productIds: List<Long>,
        onResult: (carts: List<Cart?>) -> Unit,
    ) {
        val result = dataSource.getCartsByProductIds(productIds).map { it?.toDomain() }
        onResult(result)
    }

    override fun upsert(
        productId: Long,
        quantity: Quantity,
    ) {
        val entity = CartEntity(productId, quantity.value)
        dataSource.upsert(entity)
    }

    override fun modify(
        id: Long,
        quantity: Quantity,
    ) {
        val entity = CartEntity(id, quantity.value)
        dataSource.modify(entity)
    }

    override fun delete(
        id: Long,
        onResult: (() -> Unit)?,
    ) {
        dataSource.deleteCartByProductId(id)
        onResult?.invoke()
    }

    override fun singlePage(
        page: Int,
        pageSize: Int,
        onResult: (CartSinglePage) -> Unit,
    ) {
        val result = dataSource.singlePage(page, pageSize).map { it.toDomain() }
        val pageCount = dataSource.pageCount(pageSize)
        val hasNext = page + 1 < pageCount
        onResult(CartSinglePage(result, hasNext))
    }
}
