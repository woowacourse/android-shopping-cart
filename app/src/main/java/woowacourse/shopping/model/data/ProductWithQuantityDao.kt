package woowacourse.shopping.model.data

import woowacourse.shopping.model.ProductWithQuantity

interface ProductWithQuantityDao {
    fun save(productWithQuantity: ProductWithQuantity): Long

    fun find(id: Long): ProductWithQuantity

    fun findAll(): List<ProductWithQuantity>

    fun getProducts(): List<ProductWithQuantity>

    fun getLastProducts(): List<ProductWithQuantity>

    fun plusCartCount(productWithQuantityId: Long)

    fun minusCartCount(productWithQuantityId: Long)

    fun deleteAll()
}
