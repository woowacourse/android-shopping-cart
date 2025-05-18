package woowacourse.shopping.view.model

import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.model.InventoryItem.ProductUiModel

fun Product.toUiModel(): ProductUiModel {
    return ProductUiModel(
        name,
        price,
        imageUrl,
    )
}

fun ProductUiModel.toDomain(): Product {
    return Product(
        name,
        price,
        imageUrl,
    )
}
