package woowacourse.shopping.view

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.view.uimodel.ProductUiModel
import woowacourse.shopping.view.uimodel.ShoppingCartItemUiModel

fun Product.toProductUiModel(): ProductUiModel {
    return ProductUiModel(
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl,
    )
}

fun ProductUiModel.toProduct(): Product {
    return Product(
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl,
    )
}

fun ShoppingCartItem.toShoppingCartItemUiModel(): ShoppingCartItemUiModel {
    return ShoppingCartItemUiModel(
        productUiModel = product.toProductUiModel(),
        quantity = quantity,
    )
}

fun ShoppingCartItemUiModel.toShoppingCartItem(): ShoppingCartItem {
    return ShoppingCartItem(
        product = productUiModel.toProduct(),
        quantity = quantity,
    )
}
