package model

data class CartProduct(
    val product: Product,
    val count: Count = Count(),
    val price: Price = product.price * count.value,
    val selected: Boolean = true,
) {

    fun plusCount(): CartProduct {

        return CartProduct(
            product = product,
            count = count.plus()
        )
    }

    fun minusCount(): CartProduct {

        return CartProduct(
            product = product,
            count = count.minus()
        )
    }

    fun setSelectedState(selected: Boolean): CartProduct {

        return CartProduct(
            product = product,
            count = count,
            selected = selected
        )
    }
}
