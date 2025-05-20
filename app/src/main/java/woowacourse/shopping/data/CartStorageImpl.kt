package woowacourse.shopping.data

import woowacourse.shopping.domain.Product

object CartStorageImpl : CartStorage {
    private val cartDatabase = CartDatabase

    override fun insert(item: Product) {
        cartDatabase.cart.add(item)
    }

    override fun getAll(): List<Product> = cartDatabase.cart.map { it.copy() }

    override fun deleteProduct(id: Long) {
        cartDatabase.cart.removeIf { it.id == id }
    }

    override fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        val fromIndex = page * pageSize
        val toIndex = minOf(fromIndex + pageSize, cartDatabase.cart.size)
        if (fromIndex < 0) return emptyList()
        return cartDatabase.cart.toList().subList(fromIndex, toIndex)
    }

    override fun notHasNextPage(
        page: Int,
        pageSize: Int,
    ): Boolean {
        val fromIndex = page * pageSize
        return fromIndex >= cartDatabase.cart.size
    }
}
