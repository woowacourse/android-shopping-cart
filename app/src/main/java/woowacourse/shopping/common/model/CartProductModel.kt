package woowacourse.shopping.common.model

import java.io.Serializable

data class CartProductModel(val amount: Int, val product: ProductModel) : Serializable
