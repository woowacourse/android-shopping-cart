package com.shopping.domain

class Cart(products: List<CartProduct>) {
    val products: List<CartProduct>

    init {
        this.products = products.distinctBy { it.product.id }
    }

    fun getPickedProductsTotalPrice(): Int = products
        .filter { it.isPicked }
        .sumOf { it.count * it.product.price.value }

    fun updateIsPicked(cartProduct: CartProduct, isPicked: Boolean): Cart {
        val removedProducts = products.filterNot { it.product.id == cartProduct.product.id }
        val updatedProduct = CartProduct(
            isPicked = isPicked,
            count = cartProduct.count,
            product = cartProduct.product
        )

        return Cart(removedProducts + updatedProduct)
    }

    fun getTotalPickedProductsCount(): Int = products.filter { it.isPicked }.sumOf { it.count }
}
