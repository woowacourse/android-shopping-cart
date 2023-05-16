package woowacourse.shopping.util

import model.Count
import model.Name
import model.Price
import model.Product
import model.RecentViewedProduct
import model.ShoppingCartProduct
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.model.RecentViewedProductUiModel
import woowacourse.shopping.model.ShoppingCartProductUiModel

fun ProductUiModel.toDomainModel() = Product(
    id = id,
    name = Name(name),
    imageUrl = imageUrl,
    price = Price(price)
)

fun Product.toUiModel() = ProductUiModel(
    id = id,
    name = name.value,
    imageUrl = imageUrl,
    price = price.value
)

fun RecentViewedProduct.toUiModel() = RecentViewedProductUiModel(
    id = id,
    name = name.value,
    imageUrl = imageUrl
)

fun ShoppingCartProduct.toUiModel() = ShoppingCartProductUiModel(
    id = product.id,
    name = product.name.value,
    imageUrl = product.imageUrl,
    price = price.value,
    count = count.value,
    selected = selected
)

fun ShoppingCartProductUiModel.toDomainModel() = ShoppingCartProduct(
    product = Product(
        id = id,
        name = Name(name),
        imageUrl = imageUrl,
        price = Price(price / count)
    ),
    count = Count(count),
    selected = selected
)
