package woowacourse.shopping.fixture

import woowacourse.shopping.domain.model.Goods

const val NAME: String = "[병천아우내] 모듬순대"
const val PRICE: Int = 11900

@Suppress("ktlint:standard:max-line-length")
fun createGoods(
    name: String = NAME,
    price: Int = PRICE,
): Goods =
    Goods.of(
        1,
        name,
        price,
        "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/00fb05f8-cb19-4d21-84b1-5cf6b9988749.jpg",
    )

val SUNDAE = createGoods()
val ICE_CREAM = createGoods("[빙그래] 요맘때 파인트 710mL 3종 (택1)", 5000)
