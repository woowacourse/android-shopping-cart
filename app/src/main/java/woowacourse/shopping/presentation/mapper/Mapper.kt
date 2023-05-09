package woowacourse.shopping.presentation.mapper

import woowacourse.shopping.Product
import woowacourse.shopping.presentation.model.ProductModel

fun Product.toPresentation(): ProductModel {
    return ProductModel(
        imageUrl = imageUrl,
        name = name,
        price = price.value,
    )
}
