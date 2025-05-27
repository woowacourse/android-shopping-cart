package woowacourse.shopping.data.cart

import woowacourse.shopping.domain.cart.CartDataSource
import woowacourse.shopping.domain.cart.CartProduct
import woowacourse.shopping.utils.toCartProduct

class CartDataSourceImpl(
    private val dao: CartDao,
) : CartDataSource {
    override fun findInRange(
        limit: Int,
        offset: Int,
    ): Result<List<CartProduct>> {
        return runCatching {
            dao.findCartItemsInRange(limit, offset)
        }.mapCatching { cartItemDetails ->
            cartItemDetails.map { cartItemDetail -> cartItemDetail.toCartProduct() }
        }
    }

    override fun findByProductId(productId: Long): Result<CartProduct?> {
        return runCatching {
            dao.findByProductId(productId)
        }.mapCatching { cartItemDetail ->
            cartItemDetail?.toCartProduct()
        }
    }

    override fun insertByProductId(
        productId: Long,
        quantity: Int,
    ): Result<Long> {
        return runCatching {
            val cartItemEntity = CartItemEntity(productId = productId, quantity = quantity)
            dao.insert(cartItemEntity)
        }
    }

    override fun insertOrAddQuantity(
        productId: Long,
        delta: Int,
    ): Result<Unit> {
        val cartItemEntity = CartItemEntity(productId = productId, quantity = delta)
        return runCatching {
            dao.insert(cartItemEntity)
        }.mapCatching { cartId ->
            if (cartId == EXIST_PRODUCT_IN_CART) updateQuantityByProductId(productId, delta)
        }
    }

    override fun updateQuantityByProductId(
        productId: Long,
        delta: Int,
    ): Result<Unit> {
        return runCatching {
            dao.updateQuantity(productId, delta)
        }
    }

    override fun deleteByProductId(productId: Long): Result<Unit> {
        return runCatching {
            dao.deleteByProductId(productId)
        }
    }

    override fun deleteByCartItemId(cartItemId: Long): Result<Unit> {
        return runCatching {
            dao.deleteByCartItemId(cartItemId)
        }
    }

    companion object {
        private const val EXIST_PRODUCT_IN_CART = -1L
    }
}
