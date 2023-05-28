package woowacourse.shopping.util

import model.CartProduct
import model.Count
import model.Name
import model.Price
import model.Product
import model.RecentViewedProduct
import woowacourse.shopping.model.CartProductUiModel
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.model.RecentViewedProductUiModel

fun ProductUiModel.toProductDomainModel() = Product(
    id = id,
    name = Name(name),
    imageUrl = imageUrl,
    price = Price(price)
)

fun Product.toProductUiModel() = ProductUiModel(
    id = id,
    name = name.value,
    imageUrl = imageUrl,
    price = price.value
)

fun RecentViewedProduct.toRecentViewedProductUiModel() = RecentViewedProductUiModel(
    id = id,
    name = name.value,
    imageUrl = imageUrl
)

fun CartProduct.toCartProductUiModel() = CartProductUiModel(
    id = product.id,
    name = product.name.value,
    imageUrl = product.imageUrl,
    price = price.value,
    count = count.value,
    selected = selected
)

fun CartProductUiModel.toProductDomainModel() = CartProduct(
    product = Product(
        id = id,
        name = Name(name),
        imageUrl = imageUrl,
        price = if (count == 0) {
            Price(0)
        } else {
            Price(price / count)
        }
    ),
    count = Count(count),
    selected = selected
)
