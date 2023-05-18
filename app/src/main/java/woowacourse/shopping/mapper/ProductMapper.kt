package woowacourse.shopping.mapper

import woowacourse.shopping.domain.Product
import woowacourse.shopping.model.UiProduct

fun UiProduct.toDomain(): Product =
    Product(
        id = id,
        name = name,
        price = price.toDomain(),
        imageUrl = imageUrl,
    )

fun Product.toUi(): UiProduct =
    UiProduct(
        id = id,
        name = name,
        price = price.toUi(),
        imageUrl = imageUrl,
    )
