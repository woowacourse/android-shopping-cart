package woowacourse.shopping.mapper

import com.example.domain.model.CartProduct
import woowacourse.shopping.model.CartProductUIModel

fun CartProduct.toUIModel(): CartProductUIModel {
    return CartProductUIModel(
        productUIModel = this.product.toUIModel(),
        count = this.count,
    )
}

fun CartProductUIModel.toDomain(): CartProduct {
    return CartProduct(
        product = this.productUIModel.toDomain(),
        count = this.count,
    )
}
