package woowacourse.shopping.data.dummy

import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.CartRepository
import kotlin.math.min

object DummyCartRepository : CartRepository {
    private val cartMap: MutableMap<Product, Int> = mutableMapOf()

    override fun modifyQuantity(
        product: Product,
        quantityDelta: Int,
    ): Result<Long> =
        runCatching {
            cartMap[product] = ((cartMap[product] ?: 0) + quantityDelta).coerceAtLeast(0)
            if (cartMap[product] == 0) cartMap.remove(product)
            product.id
        }

    override fun setQuantity(
        product: Product,
        newQuantityValue: Int,
    ): Result<Long> =
        runCatching {
            cartMap[product] = newQuantityValue.coerceAtLeast(0)
            if (cartMap[product] == 0) cartMap.remove(product)
            product.id
        }

    override fun deleteProduct(product: Product): Result<Long> =
        runCatching {
            cartMap.remove(product) ?: throw NoSuchElementException()
            product.id
        }

    override fun find(product: Product): Result<Cart> =
        runCatching {
            cartMap[product]?.let {
                Cart(
                    product = product,
                    quantity = it,
                )
            } ?: throw NoSuchElementException()
        }

    override fun load(
        startPage: Int,
        pageSize: Int,
    ): Result<List<Cart>> =
        runCatching {
            val carts = cartMap.map { Cart(it.key, it.value) }.toList()
            val startIndex = startPage * pageSize
            val endIndex = min(startIndex + pageSize, carts.size)
            carts.subList(startIndex, endIndex)
        }

    override fun loadAll(): Result<List<Cart>> =
        runCatching {
            cartMap.map { Cart(it.key, it.value) }.toList()
        }

    override fun getMaxPage(pageSize: Int): Result<Int> =
        runCatching {
            ((cartMap.size + (pageSize - 1)) / pageSize - 1).coerceAtLeast(0)
        }
}
