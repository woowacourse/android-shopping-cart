package woowacourse.shopping.data

import woowacourse.shopping.ShoppingApplication.Companion.database
import woowacourse.shopping.data.mapper.toDomainModel
import woowacourse.shopping.data.model.CartItemEntity
import woowacourse.shopping.data.model.mapper
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ShoppingCart
import woowacourse.shopping.domain.repository.CartRepository

class CartRepositoryImpl : CartRepository {
    private val dao = database.cartDao()

    override fun insert(
        product: Product,
        quantity: Int,
    ) {
        val thread =
            Thread {
                dao.save(product.mapper(quantity))
            }
        thread.start()
        thread.join()
    }

    override fun find(cartItemId: Long): CartItem {
        var cardItemEntity: CartItemEntity? = null
        val thread =
            Thread {
                cardItemEntity = dao.find(cartItemId)
            }
        thread.start()
        thread.join()

        return cardItemEntity?.toDomainModel() ?: throw IllegalArgumentException("데이터가 존재하지 않습니다.")
    }

    override fun findAll(): ShoppingCart {
        var cartItems: List<CartItem> = emptyList()

        val thread =
            Thread {
                cartItems = dao.findAll().map { it.toDomainModel() }
            }
        thread.start()
        thread.join()
        return ShoppingCart(cartItems)
    }

    override fun delete(cartItemId: Long) {
        val thread =
            Thread {
                dao.delete(cartItemId)
            }
        thread.start()
        thread.join()
    }

    override fun deleteAll() {
        val thread =
            Thread {
                dao.deleteAll()
            }
        thread.start()
        thread.join()
    }
}
