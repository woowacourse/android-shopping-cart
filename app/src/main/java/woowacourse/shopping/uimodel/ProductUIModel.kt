package woowacourse.shopping.uimodel

import java.io.Serializable

class ProductUIModel(
    val id: Int,
    val name: String,
    val url: String,
    val price: Int,
) : Serializable {
    companion object {
        val dummy = ProductUIModel(0, "", "", 0)
    }
}
