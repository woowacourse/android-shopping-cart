package woowacourse.shopping.util

import woowacourse.shopping.domain.model.Cart
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

fun List<Cart>.updateQuantity(
    id: Long,
    newQuantity: Int,
): List<Cart> =
    this.map { cart ->
        if (cart.goods.id == id) cart.copy(quantity = newQuantity) else cart
    }
