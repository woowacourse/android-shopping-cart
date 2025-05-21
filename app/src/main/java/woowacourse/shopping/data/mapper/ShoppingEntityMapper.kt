package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.shopping.ShoppingEntity
import woowacourse.shopping.domain.model.ShoppingGoods

fun ShoppingEntity.toShoppingGoods() =
    ShoppingGoods(
        goodsId = id,
        goodsQuantity = quantity,
    )

fun ShoppingGoods.toShoppingEntity() =
    ShoppingEntity(
        id = goodsId,
        quantity = goodsQuantity,
    )
