package woowacourse.shopping.presentation.model

import woowacourse.shopping.domain.model.Product

data class ProductUiModel(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val price: Int,
)

fun Product.toUiModel() =
    ProductUiModel(
        id,
        name,
        imageUrl,
        price.value,
    )
