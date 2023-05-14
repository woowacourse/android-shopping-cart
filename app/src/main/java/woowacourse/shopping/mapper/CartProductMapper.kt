package woowacourse.shopping.mapper

import woowacourse.shopping.model.CartProduct
import woowacourse.shopping.model.CartProductUIModel

fun CartProduct.toUIModel(): CartProductUIModel {
    return CartProductUIModel(
        id = this.id,
        name = this.name,
        price = this.price,
        imageUrl = this.imageUrl
    )
}

fun CartProductUIModel.toDomain(): CartProduct {
    return CartProduct(
        id = this.id,
        name = this.name,
        price = this.price,
        imageUrl = this.imageUrl
    )
}
