package woowacourse.shopping.ui.model

import java.io.Serializable

data class ShoppingProductModel(
    val product: ProductModel,
    val amount: Int
) : Serializable
