package woowacourse.shopping.domain

data class ShoppingCart(
    private val productMap: Map<Product, Int> = emptyMap(),
) {
    constructor(products: List<Product>) : this(products.groupingBy { it }.eachCount())

    constructor(vararg products: Product) : this(products.toList())

    fun add(product: Product): ShoppingCart {
        val count = productMap.getOrDefault(product, 0) + 1
        return ShoppingCart(productMap.plus(product to count))
    }

    fun remove(product: Product): ShoppingCart {
        return ShoppingCart(productMap.minus(product))
    }

    fun totalPrice(): Int =
        productMap.map { (product, count) ->
            product.price * count
        }.sum()

    fun productsList(): List<Products> =
        productMap.map { (product, count) ->
            Products(product, count)
        }
}
