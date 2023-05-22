package woowacourse.shopping.presentation.mapper

import woowacourse.shopping.CartProductInfo
import woowacourse.shopping.Price
import woowacourse.shopping.Product
import woowacourse.shopping.presentation.model.CartProductInfoModel
import woowacourse.shopping.presentation.model.ProductModel

fun Product.toPresentation(): ProductModel {
    return ProductModel(
        id = id,
        imageUrl = imageUrl,
        name = name,
        price = price.value,
    )
}

fun ProductModel.toDomain(): Product {
    return Product(
        id = id,
        imageUrl = imageUrl,
        name = name,
        price = Price(price),
    )
}

fun CartProductInfo.toPresentation(): CartProductInfoModel {
    return CartProductInfoModel(
        productModel = product.toPresentation(),
        count = count,
        isOrdered = isOrdered,
    )
}
