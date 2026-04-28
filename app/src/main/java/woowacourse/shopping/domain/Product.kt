package woowacourse.shopping.domain

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Product(
    val productId: Uuid = Uuid.random(),
    val imageUrl: String,
    val productName: String,
    val price: Price,
)
