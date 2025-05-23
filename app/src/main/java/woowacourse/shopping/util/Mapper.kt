package woowacourse.shopping.util

import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.Carts
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.feature.model.CartUiModel

fun Cart.toUi(): CartUiModel =
    CartUiModel(
        id = goods.id,
        name = goods.name,
        price = goods.price,
        thumbnailUrl = goods.thumbnailUrl,
        quantity = quantity,
    )

fun CartUiModel.toDomain(): Cart =
    Cart(
        quantity = quantity,
        goods =
            Goods(
                id = id,
                name = name,
                price = price,
                thumbnailUrl = thumbnailUrl,
            ),
    )

fun Carts.updateQuantity(
    id: Long,
    newQuantity: Int,
): Carts {
    val updatedCarts =
        carts.map { cart ->
            if (cart.goods.id == id) cart.copy(quantity = newQuantity) else cart
        }
    val updatedTotalQuantity = updatedCarts.sumOf { it.quantity }
    return this.copy(carts = updatedCarts, totalQuantity = updatedTotalQuantity)
}

fun Cart.updateQuantity(newQuantity: Int): Cart = this.copy(quantity = newQuantity)
