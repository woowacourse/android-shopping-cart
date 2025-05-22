package woowacourse.shopping.presentation.model

import woowacourse.shopping.domain.model.CartItem

data class CatalogItemUiModel(
    val productId: Long,
    val imageUrl: String,
    val productName: String,
    val price: Int,
    var quantity: Int,
)

fun CartItem.toCatalogItem() =
    CatalogItemUiModel(
        productId = product.id,
        productName = product.name,
        imageUrl = product.imageUrl,
        price = product.price.value,
        quantity = quantity,
    )
