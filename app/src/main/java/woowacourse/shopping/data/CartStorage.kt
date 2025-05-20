package woowacourse.shopping.data

import woowacourse.shopping.domain.Product

interface CartStorage {
    fun insert(item: Product)

    fun getAll(): List<Product>

    fun totalSize(): Int

    fun deleteProduct(id: Long)

    fun slice(range: IntRange): List<Product>
}
