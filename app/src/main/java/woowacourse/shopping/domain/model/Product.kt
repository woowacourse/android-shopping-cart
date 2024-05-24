package woowacourse.shopping.domain.model

import java.io.Serializable

data class Product(
    val id: Long,
    val name: String,
    val price: Int,
    val quantity: Int = INIT_QUANTITY_NUM,
    val imageUrl: String,
) : Serializable {
    val totalPrice: Int
        get() = price * quantity

    companion object {
        const val INIT_QUANTITY_NUM = 0
    }
}
