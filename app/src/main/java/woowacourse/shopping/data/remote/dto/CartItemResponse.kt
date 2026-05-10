package woowacourse.shopping.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
class CartItemResponse(
    val id: Int,
    val quantity: Int,
    val product: ProductResponse,
) {
}
