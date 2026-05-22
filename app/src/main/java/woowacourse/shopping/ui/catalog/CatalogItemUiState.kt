package woowacourse.shopping.ui.catalog

import woowacourse.shopping.domain.Product

data class CatalogItemUiState(
    val product: Product,
    val quantity: Int,
)
