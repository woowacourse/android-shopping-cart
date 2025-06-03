package woowacourse.shopping.util

import woowacourse.shopping.feature.model.GoodsItem

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
