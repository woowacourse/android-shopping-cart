package woowacourse.shopping.view.inventory.item

import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.inventory.item.InventoryItem.ProductUiModel

fun Product.toUiModel(): ProductUiModel {
    return ProductUiModel(
        id,
        name,
        price,
        imageUrl,
    )
}

fun ProductUiModel.toDomain(): Product {
    return Product(
        id,
        name,
        price,
        imageUrl,
    )
}
