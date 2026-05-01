package woowacourse.shopping.domain

import java.util.UUID

class Products(
    val products: List<Product> = emptyList(),
) {
    fun size() = products.size

    fun add(product: Product) = Products(products + product)

    fun remove(id: UUID): Products {
        val product = findWithId(id) ?: return this
        return Products(products - product)
    }

    fun findWithId(id: UUID) = products.find { it.uuid == id }
}
