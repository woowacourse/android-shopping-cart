package woowacourse.shopping.domain

import java.util.UUID

class CartProducts(
    val products: List<Product> = emptyList()
){
    fun size() = products.size
    fun add(product: Product) = CartProducts(products + product)
    fun remove(id: UUID): CartProducts {
        val product = findWithId(id) ?: return this
        return CartProducts(products - product)
    }
    fun findWithId(id: UUID) = products.find { it.uuid == id }
}
