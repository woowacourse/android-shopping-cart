package woowacourse.shopping.domain

class Cart(private val cartContents: List<CartContent>) {
    fun plusCartContent(newCartContent: CartContent): Cart {
        val isSamerCartItem = cartContents.any { cartContent ->
            cartContent.hasProductId(newCartContent.productId)
        }

        if (isSamerCartItem) {
            return this
        }
        return Cart(cartContents + newCartContent)
    }

    fun contains(product: Product): Boolean = cartContents.any { it.hasProductId(product.id) }

    fun retainOnly(ids: List<String>): Cart {
        return Cart(
            cartContents.filter { cartItem ->
                ids.any {
                    cartItem.hasProductId(it)
                }
            },
        )
    }

    fun getProductList(): List<Product> {
        return cartContents.map { it.product }
    }
}
