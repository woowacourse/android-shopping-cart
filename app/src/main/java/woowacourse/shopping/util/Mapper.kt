package woowacourse.shopping.util

import model.CartProduct
import model.Name
import model.Price
import model.Product
import woowacourse.shopping.model.CartProductUiModel
import woowacourse.shopping.model.ProductUiModel

fun ProductUiModel.toDomainModel() = Product(
    id = id,
    name = Name(name),
    imageUrl = imageUrl,
    price = Price(price),
)

fun Product.toUiModel() = ProductUiModel(
    id = id,
    name = name.value,
    imageUrl = imageUrl,
    price = price.value,
)

fun CartProductUiModel.toDomainModel() = CartProduct(
    product = product.toDomainModel(),
    count = count,
    isSelected = isSelected,
)

fun CartProduct.toUiModel() = CartProductUiModel(
    product = product.toUiModel(),
    count = count,
    isSelected = isSelected,
)
