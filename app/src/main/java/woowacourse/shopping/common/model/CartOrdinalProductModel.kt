package woowacourse.shopping.common.model

import java.io.Serializable

data class CartOrdinalProductModel(
    val ordinal: Int,
    val cartProduct: CartProductModel
) : Serializable
