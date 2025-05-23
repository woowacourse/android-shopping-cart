package woowacourse.shopping.view.inventory.item

import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.inventory.item.InventoryItem.InventoryProduct

fun Product.toUiModel(): InventoryProduct {
    return InventoryProduct(
        id,
        name,
        price,
        0,
        imageUrl,
    )
}

fun InventoryProduct.toDomain(): Product {
    return Product(
        id,
        name,
        price,
        imageUrl,
    )
}
