package woowacourse.shopping.fixture

import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.domain.model.ShoppingGoods
import woowacourse.shopping.presentation.model.GoodsUiModel
import woowacourse.shopping.presentation.model.toUiModel

const val NAME: String = "[병천아우내] 모듬순대"
const val PRICE: Int = 11900
var id: Int = 1

@Suppress("ktlint:standard:max-line-length")
fun createGoods(
    name: String = NAME,
    price: Int = PRICE,
): GoodsUiModel =
    Goods.of(
        id,
        name,
        price,
        "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/00fb05f8-cb19-4d21-84b1-5cf6b9988749.jpg",
    ).toUiModel()

fun createShoppingGoods(
    goodsId: Int = 1,
    goodsQuantity: Int = 2,
): ShoppingGoods =
    ShoppingGoods(
        goodsId,
        goodsQuantity,
    )
