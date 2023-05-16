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

    // todo: 이상한 부분
    fun minusCount(): ShoppingCartProduct {

        return ShoppingCartProduct(
            product = product,
            count = if (count.value == 1) {
                count
            } else {
                count.minus()
            }
        )
    }

    fun setSelectedState(selected: Boolean): ShoppingCartProduct {

        return ShoppingCartProduct(
            product = product,
            count = count,
            selected = selected
        )
    }
}
