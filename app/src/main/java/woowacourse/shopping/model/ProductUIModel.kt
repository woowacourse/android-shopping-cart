package woowacourse.shopping.model

import java.io.Serializable

data class ProductUIModel(
    val id: Long,
    val name: String,
    val price: Int,
    val imageUrl: String,
) : Serializable {
    companion object {
        const val KEY_PRODUCT = "KEY_PRODUCT"
    }
}
