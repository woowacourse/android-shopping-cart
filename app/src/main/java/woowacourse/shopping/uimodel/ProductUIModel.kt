package woowacourse.shopping.uimodel

import java.io.Serializable

data class ProductUIModel(
    val id: Int,
    val imageUrl: String,
    val name: String,
    val price: Int,
) : Serializable {
    companion object {
        val dummy = ProductUIModel(Integer.MIN_VALUE, "", "", 0)
    }
}
