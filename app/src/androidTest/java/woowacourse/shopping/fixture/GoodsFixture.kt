package woowacourse.shopping.fixture

import woowacourse.shopping.domain.model.ShoppingGoods

fun createShoppingGoods(
    goodsId: Int = 1,
    goodsQuantity: Int = 2,
): ShoppingGoods =
    ShoppingGoods(
        goodsId,
        goodsQuantity,
    )
