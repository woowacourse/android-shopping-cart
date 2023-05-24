package woowacourse.shopping.mapper

import woowacourse.shopping.model.CartProduct
import woowacourse.shopping.model.CartProductUIModel

fun CartProduct.toUIModel(): CartProductUIModel {
    return CartProductUIModel(
        id = this.id,
        name = this.name,
        count = this.count,
        checked = this.checked,
        price = this.price,
        imageUrl = this.imageUrl
    )
}
