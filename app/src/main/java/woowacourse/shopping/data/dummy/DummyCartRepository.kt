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
            cartMap[product]?.let { originQuantity ->
                val newQuantity = originQuantity + quantityDelta
                updateQuantity(product, newQuantity)
            } ?: addCart(product, 0 + quantityDelta)
            product.id
        }

    override fun updateQuantity(
        product: Product,
        newQuantity: Int,
    ): Result<Long> =
        runCatching {
            when {
                (newQuantity == 0) -> deleteProduct(product)
                (0 < newQuantity) -> cartMap[product] = newQuantity
                else -> throw IllegalArgumentException()
            }
            product.id
        }

    private fun addCart(
        product: Product,
        newQuantity: Int,
    ) {
        if (newQuantity <= 0) throw IllegalArgumentException("장바구니 상품의 수량은 0보다 커야합니다.")
        cartMap.plus(Pair(product, newQuantity))
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
            if (pageSize < 1) throw IllegalArgumentException("pageSize는 1 이상이어야 합니다.")
            ((cartMap.size + (pageSize - 1)) / pageSize - 1)
        }
}
