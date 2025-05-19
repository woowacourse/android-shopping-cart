package woowacourse.shopping.data.cart

import kotlin.concurrent.thread
import woowacourse.shopping.domain.cart.CartProduct
import woowacourse.shopping.domain.cart.CartRepository
import woowacourse.shopping.domain.product.Money
import woowacourse.shopping.domain.product.Product

class CartRepositoryImpl(private val dao: CartDao) : CartRepository {
    override fun insert(product: Product) {
        thread {
            val cartItemEntity = CartItemEntity(productId = product.id!!)
            dao.insert(cartItemEntity)
        }
    }

    override fun fetchById(
        cartItemId: Long,
        onResult: (CartProduct) -> Unit,
    ) {
        thread {
            val cartItemDetail: CartItemDetail = dao.findByCartItemId(cartItemId)
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

    override fun delete(cartItemId: Long) {
        thread {
            dao.delete(cartItemId)
        }
    }
}

private fun CartItemDetail.toDomain(): CartProduct {
    return CartProduct(
        this.cartItem.id,
        this.cartItem.productId,
        this.productEntity.imageUrl,
        this.productEntity.name,
        Money(this.productEntity.price),
    )
}
