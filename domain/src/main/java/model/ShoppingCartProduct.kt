package model

data class ShoppingCartProduct(
    val product: Product,
    val count: Count = Count(),
    val price: Price = product.price * count.value,
    val selected: Boolean = true,
) {

    fun plusCount(): ShoppingCartProduct {

        return ShoppingCartProduct(
            product = product,
            count = count.plus()
        )
    }

    fun minusCount(): ShoppingCartProduct {

        return ShoppingCartProduct(
            product = product,
            count = count.minus()
        )
    }
}
