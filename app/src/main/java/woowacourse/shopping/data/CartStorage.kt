package woowacourse.shopping.data

import woowacourse.shopping.domain.Product

interface CartStorage {
    fun insert(item: Product)

    fun getAll(): List<Product>

    fun deleteProduct(id: Long)
}
