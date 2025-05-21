package woowacourse.shopping.presentation.model

import woowacourse.shopping.domain.model.Product

data class CatalogItemUiModel(
    val productId: Long,
    val imageUrl: String,
    val productName: String,
    val price: Int,
    var quantity: Int = 1,
    var isOpenQuantitySelector: Boolean = false,
)

fun Product.toCatalogItemUiModel() =
    CatalogItemUiModel(
        productId = id,
        productName = name,
        imageUrl = imageUrl,
        price = price.value,
    )
