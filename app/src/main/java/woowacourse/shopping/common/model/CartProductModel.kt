package woowacourse.shopping.common.model

import java.io.Serializable
import java.time.LocalDateTime

data class CartProductModel(
    val time: LocalDateTime,
    val amount: Int,
    val isChecked: Boolean,
    val product: ProductModel
) : Serializable
