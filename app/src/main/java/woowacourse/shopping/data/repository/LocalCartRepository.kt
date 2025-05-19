package woowacourse.shopping.data.repository

import woowacourse.shopping.data.dao.CartDao
import woowacourse.shopping.data.entity.CartProductEntity
import woowacourse.shopping.data.mapper.toData
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.model.CartProducts
import woowacourse.shopping.domain.repository.CartRepository
import kotlin.concurrent.thread

class LocalCartRepository(
    private val cartDao: CartDao,
) : CartRepository {
    override fun getCartProduct(
        productId: Int,
        callback: (CartProduct?) -> Unit,
    ) {
        thread {
            callback(
                cartDao.getCartProductDetailById(productId)?.toDomain(),
            )
        }
    }

    override fun getCartProducts(
        page: Int,
        size: Int,
        callback: (CartProducts) -> Unit,
    ) {
        thread {
            callback(
                CartProducts(
                    products = cartDao.getCartProductDetails(page, size).map { it.toDomain() },
                    maxPage = cartDao.getMaxPageCount(size),
                ),
            )
        }
    }

    override fun increaseProductQuantity(
        productId: Int,
        quantity: Int,
        callback: (Int) -> Unit,
    ) {
        thread {
            val existing = cartDao.getCartProductById(productId)
            if (existing != null) {
                cartDao.insertCartProduct(
                    existing.copy(quantity = existing.quantity + quantity),
                )
            } else {
                cartDao.insertCartProduct(
                    CartProductEntity(productId, quantity),
                )
            }
            callback(existing?.quantity?.plus(quantity) ?: quantity)
        }
    }

    override fun decreaseProductQuantity(
        productId: Int,
        quantity: Int,
        callback: (Int) -> Unit,
    ) {
        thread {
            val existing = cartDao.getCartProductById(productId)
            if (existing != null) {
                val newQuantity = existing.quantity - quantity
                if (newQuantity <= 0) {
                    cartDao.deleteCartProduct(productId)
                } else {
                    cartDao.insertCartProduct(
                        existing.copy(quantity = newQuantity),
                    )
                }
            }
            callback(existing?.quantity?.minus(quantity) ?: 0)
        }
    }

    override fun removeCartProduct(productId: Int) {
        thread {
            cartDao.deleteCartProduct(productId)
        }
    }

    override fun updateCartProduct(cartProduct: CartProduct) {
        thread {
            cartDao.insertCartProduct(cartProduct.toData())
        }
    }
}
