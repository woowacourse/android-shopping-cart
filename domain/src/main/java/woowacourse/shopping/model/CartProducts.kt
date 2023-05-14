package woowacourse.shopping.model

data class CartProducts(private val cartProducts: List<CartProduct>) {
    val size: Int = cartProducts.size

    operator fun get(index: Int): CartProduct {
        return cartProducts[index]
    }

    fun all(): List<CartProduct> {
        return cartProducts.toList()
    }

    fun subList(offset: Int, size: Int): CartProducts {
        val lastIndex = cartProducts.lastIndex
        val endIndex = (lastIndex + 1).coerceAtMost(offset + size)

        return when (offset) {
            in 0..lastIndex -> CartProducts(cartProducts.subList(offset, endIndex))
            else -> CartProducts(emptyList())
        }
    }

    fun hasNextPage(index: Int, size: Int): Boolean {
        return index * size + size < cartProducts.size
    }

    fun hasPrevPage(index: Int, size: Int): Boolean {
        return index * size - size >= 0
    }
}
