package woowacourse.shopping.data

import woowacourse.shopping.domain.Product

object CartStorageImpl : CartStorage {
    private val cart: MutableList<Product> = mutableListOf()

    override fun insert(item: Product) {
        cart.add(item)
    }

    override fun getAll(): List<Product> = cart.map { it.copy() }

    override fun deleteProduct(id: Long) {
        cart.removeIf { it.id == id }
    }
}
