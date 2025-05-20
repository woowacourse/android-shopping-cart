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
    private val dao: CartDao,
) : CartRepository {
    override fun fetchCartProduct(
        productId: Int,
        callback: (CartProduct?) -> Unit,
    ) {
        thread {
            callback(
                dao.getCartProductDetailById(productId)?.toDomain(),
            )
        }
    }

    override fun fetchCartProducts(
        page: Int,
        size: Int,
        callback: (CartProducts) -> Unit,
    ) {
        thread {
            callback(
                CartProducts(
                    products = dao.getCartProductDetails(page, size).map { it.toDomain() },
                    maxPage = dao.getMaxPageCount(size),
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
            val existing = dao.getCartProductById(productId)
            if (existing != null) {
                dao.insertCartProduct(
                    existing.copy(quantity = existing.quantity + quantity),
                )
            } else {
                dao.insertCartProduct(
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
            val existing = dao.getCartProductById(productId)
            if (existing != null) {
                val newQuantity = existing.quantity - quantity
                if (newQuantity <= 0) {
                    dao.deleteCartProduct(productId)
                } else {
                    dao.insertCartProduct(
                        existing.copy(quantity = newQuantity),
                    )
                }
            }
            callback(existing?.quantity?.minus(quantity) ?: 0)
        }
    }

    override fun removeCartProduct(productId: Int) {
        thread {
            dao.deleteCartProduct(productId)
        }
    }

    override fun saveCartProduct(cartProduct: CartProduct) {
        thread {
            dao.insertCartProduct(cartProduct.toData())
        }
    }
}
