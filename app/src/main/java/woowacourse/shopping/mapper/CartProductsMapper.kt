package woowacourse.shopping.mapper

import woowacourse.shopping.model.CartProductUIModel
import woowacourse.shopping.model.CartProducts

fun CartProducts.toUIModel(): List<CartProductUIModel> {
    return this.all().map { it.toUIModel() }
}
