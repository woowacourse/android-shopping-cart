package woowacourse.shopping.util

import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.feature.model.CartUiModel
import woowacourse.shopping.feature.model.GoodsItem

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

fun List<GoodsItem.Product>.updateCartQuantity(
    id: Long,
    newQuantity: Int,
): List<GoodsItem.Product> =
    this.map { item ->
        if (item.cart.goods.id == id) {
            GoodsItem.Product(item.cart.copy(quantity = newQuantity))
        } else {
            item
        }
    }

fun Cart.updateQuantity(newQuantity: Int): Cart = this.copy(quantity = newQuantity)
