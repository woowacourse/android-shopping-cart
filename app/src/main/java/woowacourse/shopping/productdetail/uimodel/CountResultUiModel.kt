package woowacourse.shopping.productdetail.uimodel

import woowacourse.shopping.domain.Price

data class CountResultUiModel(
    val count: Int,
    val price: Price,
) {
    val totalPrice: Int = price.times(count).value
}
