package woowacourse.shopping.data

import woowacourse.shopping.domain.Product

class FakeCartStorage(
    private val products: MutableList<Product> = mutableListOf(),
) : CartStorage {
    override fun insert(item: Product) {
        products.add(item)
    }

    override fun getAll(): List<Product> = products.toList()

    override fun totalSize(): Int = products.size

    override fun deleteProduct(id: Long) {
        products.removeIf { it.id == id }
    }

    override fun slice(range: IntRange): List<Product> {
        return products.slice(range)
    }
}
