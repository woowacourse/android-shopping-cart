package woowacourse.shopping.common.model

import java.io.Serializable

data class CheckableCartProductModel(val checked: Boolean, val cartProduct: CartProductModel) :
    Serializable
