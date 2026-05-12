package woowacourse.shopping.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import woowacourse.shopping.data.local.dao.CartDao
import woowacourse.shopping.data.local.entity.CartItemEntity
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Money
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.repository.CartRepository

class CartRepositoryImpl(private val cartDao: CartDao) : CartRepository {
    override fun getCart(): Flow<List<CartItem>> = cartDao.getCartItems().map { entity ->
        entity.map { it.toCartItem() }
    }

    override suspend fun updateCartItem(cartItem: CartItem) {
        cartDao.insertOrUpdate(cartItem.toEntity())
    }

    override suspend fun deleteCartItem(productId: String) {
        cartDao.deleteById(productId)
    }

    private fun CartItem.toEntity(): CartItemEntity = CartItemEntity(
        productId = product.id,
        name = product.name,
        price = product.price.amount,
        imageUrl = product.imageUrl,
        quantity = quantity.count,
    )

    private fun CartItemEntity.toCartItem(): CartItem = CartItem(
        product = Product(
            name = name,
            price = Money(price),
            imageUrl = imageUrl,
            id = productId,
        ),
        quantity = Quantity(quantity),
    )
}
