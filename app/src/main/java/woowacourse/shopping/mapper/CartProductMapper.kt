package woowacourse.shopping.mapper

import com.domain.model.CartProduct
import woowacourse.shopping.model.CartProductUIModel

fun CartProduct.toUIModel(): CartProductUIModel {
    return CartProductUIModel(
        product = this.product.toUIModel(),
        count = this.count,
    )
}

fun CartProductUIModel.toDomain(): CartProduct {
    return CartProduct(
        product = this.product.toDomain(),
        count = this.count,
    )
}
