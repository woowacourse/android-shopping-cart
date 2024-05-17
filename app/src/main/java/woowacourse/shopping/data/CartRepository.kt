package woowacourse.shopping.data

import woowacourse.shopping.model.Product

class CartRepository {

    private val cartProducts: MutableList<Product> = mutableListOf()
    fun addCartProduct(product: Product) {
        cartProducts.add(product)
    }

    fun getAllCartProducts(): List<Product> {
        return cartProducts.toList()
    }

    fun deleteCartProduct(id: Int) {
        cartProducts.removeAt(id)
    }
}
