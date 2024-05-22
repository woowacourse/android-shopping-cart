package woowacourse.shopping.model.data

import woowacourse.shopping.model.ProductWithQuantity
import kotlin.math.min

object ProductWithQuantitiesImpl : ProductWithQuantityDao {
    private var currentOffset = 0
    private const val LOAD_LIMIT = 20
    private const val EXCEPTION_INVALID_ID = "Product not found with id: %d"
    private var id: Long = 0
    private val productWithQuantities = mutableMapOf<Long, ProductWithQuantity>()

    init {
        ProductsImpl.findAll().forEach {
            save(ProductWithQuantity(product = it))
        }
    }

    override fun save(productWithQuantity: ProductWithQuantity): Long {
        productWithQuantities[id] = productWithQuantity.copy(id = id)
        return id++
    }

    override fun deleteAll() {
        productWithQuantities.clear()
    }

    override fun find(id: Long): ProductWithQuantity {
        return productWithQuantities[id] ?: throw NoSuchElementException(invalidIdMessage(id))
    }

    override fun findAll(): List<ProductWithQuantity> {
        return productWithQuantities.map { it.value }
    }

    override fun getProducts(): List<ProductWithQuantity> {
        val fromIndex = currentOffset
        currentOffset = min(currentOffset + LOAD_LIMIT, productWithQuantities.size)
        return productWithQuantities.values.toList().subList(fromIndex, currentOffset)
    }

    override fun getLastProducts(): List<ProductWithQuantity> {
        return productWithQuantities.values.toList().subList(0, currentOffset)
    }

    override fun plusCartCount(productWithQuantityId: Long) {
        productWithQuantities[productWithQuantityId]?.let {
            productWithQuantities[productWithQuantityId] =
                it.inc()
        }
    }

    override fun minusCartCount(productWithQuantityId: Long) {
        productWithQuantities[productWithQuantityId]?.let {
            productWithQuantities[productWithQuantityId] = it.dec()
        }
    }

    private fun invalidIdMessage(id: Long) = EXCEPTION_INVALID_ID.format(id)
}
