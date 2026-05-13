package woowacourse.shopping.data.repository

import woowacourse.shopping.data.local.dao.CartDao
import woowacourse.shopping.data.local.mapper.toCartItemEntity
import woowacourse.shopping.data.local.mapper.toDomain
import woowacourse.shopping.domain.model.cart.Cart
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository

class CartRepositoryImpl(
    private val cartDao: CartDao,
    private val productRepository: ProductRepository,
) : CartRepository {
    override suspend fun getItems(): Cart = Cart(cartDao.findAll().map { it.toDomain() })

    override suspend fun getPagingItems(
        page: Int,
        pageSize: Int,
    ): Cart {
        if (page < 0 || pageSize <= 0) return Cart()

        val offset = page * pageSize

        return Cart(
            cartItems =
                cartDao
                    .findPagingItems(
                        limit = pageSize,
                        offset = offset,
                    ).map { it.toDomain() },
        )
    }

    override suspend fun getTotalItemCount(): Int = cartDao.countItems()

    override suspend fun getTotalQuantity(): Int = cartDao.sumQuantity()

    override suspend fun increaseQuantity(
        productId: Int,
        quantity: Int,
    ) {
        val savedItem = cartDao.findByProductId(productId)

        if (savedItem != null) {
            cartDao.save(savedItem.copy(quantity = savedItem.quantity + quantity))
        } else {
            val product =
                productRepository.findProductById(productId)
                    ?: throw Exception("Product not found")
            cartDao.save(product.toCartItemEntity(quantity = quantity))
        }
    }

    override suspend fun decreaseQuantity(productId: Int) {
        val savedItem = cartDao.findByProductId(productId) ?: return

        if (savedItem.quantity == 1) {
            cartDao.deleteByProductId(productId)
            return
        }

        cartDao.save(
            savedItem.copy(quantity = savedItem.quantity - 1),
        )
    }

    override suspend fun deleteProduct(productId: Int) {
        cartDao.deleteByProductId(productId)
    }
}
