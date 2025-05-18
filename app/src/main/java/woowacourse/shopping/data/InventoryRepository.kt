package woowacourse.shopping.data

import woowacourse.shopping.domain.Product

interface InventoryRepository {
    fun getAll(): List<Product>
}
