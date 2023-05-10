package woowacourse.shopping.util

import model.Product
import woowacourse.shopping.model.ProductUiModel

fun ProductUiModel.toDomainModel() = Product(
    id = id,
    name = name,
    imageUrl = imageUrl,
    price = price
)

fun Product.toUiModel() = ProductUiModel(
    id = id,
    name = name,
    imageUrl = imageUrl,
    price = price
)
