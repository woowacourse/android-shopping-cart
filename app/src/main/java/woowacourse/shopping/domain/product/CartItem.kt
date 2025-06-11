package woowacourse.shopping.domain.product

import java.io.Serializable

data class CartItem(
    private val product: Product,
    val quantity: Int = 0,
) : Serializable {
    constructor(id: Long, name: String, price: Int, quantity: Int = 0) :
        this(Product(id, name, price), quantity)

    val id: Long = product.id
    val name: String = product.name
    val price: Int = product.price
}
