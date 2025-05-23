package woowacourse.shopping.data.cart

import kotlin.concurrent.thread
import woowacourse.shopping.domain.cart.CartProduct
import woowacourse.shopping.domain.cart.CartRepository
import woowacourse.shopping.utils.toProduct

class CartRepositoryImpl(private val dao: CartDao) : CartRepository {
    override fun insert(
        productId: Long,
        quantity: Int,
        onResult: (Result<Long>) -> Unit
    ) {
        thread {
            val cartItemEntity = CartItemEntity(
                productId = productId,
                quantity = quantity
            )
            runCatching {
                dao.insert(cartItemEntity)
            }.onSuccess { result ->
                onResult(Result.success(result))
            }.onFailure {
                onResult(Result.failure(it))
            }
        }
    }

    override fun insertOrAddQuantity(
        productId: Long,
        quantity: Int,
        onResult: (Result<Unit>) -> Unit
    ) {
        thread {
            val cartItemEntity = CartItemEntity(productId = productId, quantity = quantity)
            runCatching {
                dao.insert(cartItemEntity)
            }.onSuccess { cartId ->
                if (cartId == EXIST_PRODUCT_IN_CART) {
                    dao.addQuantity(productId, quantity)
                }
                
                onResult(Result.success(Unit))
            }.onFailure {
                onResult(Result.failure(it))
            }

        }
    }

    override fun fetchByProductId(
        productId: Long,
        onResult: (CartProduct) -> Unit,
    ) {
        thread {
            val cartItemDetail: CartItemDetail = dao.findByCartItemId(productId)
            onResult(cartItemDetail.toDomain())
        }
    }

    override fun fetchInRange(
        limit: Int,
        offset: Int,
        onResult: (List<CartProduct>) -> Unit,
    ) {
        thread {
            val cartItemDetails: List<CartItemDetail> = dao.findCartItemsInRange(limit, offset)
            val cartProducts: List<CartProduct> =
                cartItemDetails.map { cartItemDetail -> cartItemDetail.toDomain() }
            onResult(cartProducts)
        }
    }

    override fun delete(
        cartItemId: Long,
        onResult: (Unit) -> Unit,
    ) {
        thread {
            runCatching {
                dao.delete(cartItemId)
            }.onSuccess { onResult(Unit) }
        }
    }

    companion object {
        private const val EXIST_PRODUCT_IN_CART = -1L
    }
}

private fun CartItemDetail.toDomain(): CartProduct {
    return CartProduct(
        this.cartItemEntity.id,
        this.productEntity.toProduct(),
        this.cartItemEntity.quantity
    )
}
