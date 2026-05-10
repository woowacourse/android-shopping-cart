package woowacourse.shopping.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import woowacourse.shopping.data.local.dao.CartDao
import woowacourse.shopping.data.local.entity.CartItemEntity
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Money
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.repository.CartRepository

class CartRepositoryImpl(private val cartDao: CartDao) : CartRepository {
    override fun getCart(): Flow<List<CartItem>> = cartDao.getCartItems().map { entity ->
        entity.map { it.toCartItem() }
    }

    override suspend fun addCartItem(
        product: Product,
        quantity: Quantity,
    ) {
        val currentEntity = cartDao.getCartItems().first()
        val currentItems = currentEntity.map { it.toCartItem() }
        val cart = Cart(currentItems)

        val updateCart = cart.plusProduct(product, quantity)
        val updateCartItem = updateCart.cartItems.find { it.hasProduct(product) } ?: throw IllegalArgumentException("상품을 찾을 수 없습니다")
        cartDao.insertOrUpdate(updateCartItem.toEntity())
    }

    override suspend fun decreaseCartItem(
        product: Product,
        quantity: Quantity,
    ) {
        val currentEntity = cartDao.getCartItems().first()
        val currentItems = currentEntity.map { it.toCartItem() }
        val cart = Cart(currentItems)

        val updateCart = cart.minusProduct(product, quantity)
        val updateCartItem = updateCart.cartItems.find { it.hasProduct(product) }

        if (updateCartItem == null) {
            cartDao.deleteById(product.id)
        } else {
            cartDao.insertOrUpdate(updateCartItem.toEntity())
        }
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
