package woowacourse.shopping.feature.fake

import woowacourse.shopping.data.repository.cart.CartRepository
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartContent
import woowacourse.shopping.domain.Product

class FakeCartRepository(
    initial: List<CartContent> = emptyList(),
) : CartRepository {

    private val contents: MutableList<CartContent> = initial.toMutableList()

    override suspend fun loadCart(): Cart = Cart(contents.toList())

    override suspend fun loadCartContents(): List<CartContent> = contents.toList()

    override suspend fun loadCartSize(): Int = contents.size

    override suspend fun increase(product: Product) {
        val index = indexOf(product.id)
        if (index == -1) {
            contents.add(CartContent(product, 1))
        } else {
            val existing = contents[index]
            contents[index] = CartContent(existing.product, existing.quantity + 1)
        }
    }

    override suspend fun decrease(productId: String) {
        val index = indexOf(productId)
        if (index == -1) return
        val existing = contents[index]
        if (existing.quantity <= 1) {
            contents.removeAt(index)
        } else {
            contents[index] = CartContent(existing.product, existing.quantity - 1)
        }
    }

    override suspend fun pagination(
        startIndex: Int,
        pageSize: Int,
    ): List<CartContent> {
        if (startIndex >= contents.size) return emptyList()
        val end = minOf(startIndex + pageSize, contents.size)
        return contents.subList(startIndex, end).toList()
    }

    override suspend fun remove(productId: String) {
        val index = indexOf(productId)
        if (index != -1) contents.removeAt(index)
    }

    override suspend fun setProductQuantity(
        productId: String,
        quantity: Int,
    ) {
        if (quantity < 1) return
        val index = indexOf(productId)
        if (index == -1) return
        val existing = contents[index]
        contents[index] = CartContent(existing.product, quantity)
    }

    private fun indexOf(productId: String): Int = contents.indexOfFirst { it.hasProductId(productId) }
}
