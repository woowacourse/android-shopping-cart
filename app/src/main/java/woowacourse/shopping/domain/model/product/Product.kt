package woowacourse.shopping.domain.model.product

import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
data class Product(
    val productId: Int,
    val imageUrl: String,
    val productName: String,
    val price: Price,
)
