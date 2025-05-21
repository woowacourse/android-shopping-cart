package woowacourse.shopping.presentation.model

import woowacourse.shopping.domain.model.CartItem

data class CatalogItemUiModel(
    val productId: Long,
    val imageUrl: String,
    val productName: String,
    val price: Int,
    var quantity: Int,
    var isOpenQuantitySelector: Boolean = false,
)

fun CartItem.toCatalogItem(isOpenQuantitySelector: Boolean) =
    CatalogItemUiModel(
        productId = product.id,
        productName = product.name,
        imageUrl = product.imageUrl,
        price = totalPrice,
        quantity = quantity,
        isOpenQuantitySelector = isOpenQuantitySelector,
    )
