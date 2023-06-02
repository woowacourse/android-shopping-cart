package com.shopping.domain

class Cart(products: List<CartProduct> = emptyList()) {
    val products: List<CartProduct>

    init {
        this.products = products.distinctBy { it.product.id }
    }

    fun addAll(anotherCart: Cart): Cart = Cart(products + anotherCart.products)

    fun addAll(cartProducts: List<CartProduct>): Cart = Cart(products + cartProducts)

    fun remove(product: CartProduct): Cart = Cart(products - product)

    fun removeAll(anotherCart: Cart): Cart = Cart(products - anotherCart.products)

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

    fun updateProductCount(cartProduct: CartProduct, count: Int): Cart {
        val removedProducts = products.filterNot { it.product.id == cartProduct.product.id }
        val newProduct = CartProduct(cartProduct.isPicked, count, cartProduct.product)
        return Cart(removedProducts + newProduct)
    }

    fun getTotalPickedProductsCount(): Int = products.count { it.isPicked }

    fun isAllPicked(): Boolean = products.count() == products.count { it.isPicked }

    fun setIsPickAllProduct(isPick: Boolean): Cart =
        Cart(products.map { CartProduct(isPick, it.count, it.product) })
}
