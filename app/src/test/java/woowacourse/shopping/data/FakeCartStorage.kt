package woowacourse.shopping.data

import woowacourse.shopping.domain.Product

class FakeCartStorage(
    private val products: MutableList<Product> = mutableListOf(),
) : CartStorage {
    override fun insert(item: Product) {
        products.add(item)
    }

    override fun getAll(): List<Product> = products.toList()

    override fun deleteProduct(id: Long) {
        products.removeIf { it.id == id }
    }

    override fun subList(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        val fromIndex = page * pageSize
        val toIndex = minOf(fromIndex + pageSize, products.size)
        if (fromIndex < 0 || fromIndex >= products.size) return emptyList()
        return products.subList(fromIndex, toIndex)
    }

    override fun notHasNextPage(
        page: Int,
        pageSize: Int,
    ): Boolean {
        val fromIndex = page * pageSize
        return fromIndex >= products.size
    }
}
