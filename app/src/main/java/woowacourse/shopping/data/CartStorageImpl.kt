package woowacourse.shopping.data

import woowacourse.shopping.domain.Product

object CartStorageImpl : CartStorage {
    private val cartDatabase = CartDatabase

    override fun insert(item: Product) {
        cartDatabase.cart.add(item)
    }

    override fun getAll(): List<Product> = cartDatabase.cart.map { it.copy() }

    override fun totalSize(): Int = cartDatabase.cart.size

    override fun deleteProduct(id: Long) {
        cartDatabase.cart.removeIf { it.id == id }
    }

    override fun slice(range: IntRange): List<Product> {
        return cartDatabase.cart.toList().slice(range)
    }
}
