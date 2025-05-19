package woowacourse.shopping.data

import android.util.Log
import woowacourse.shopping.model.Product

object CartRepository {

    private val cartProducts: MutableList<Product> = mutableListOf()
    fun addCartProduct(product: Product) {
        cartProducts.add(product)
        Log.e("dino_log", "addCartProduct: ${cartProducts.size}")
    }

    fun getAllCartProducts(): List<Product> {

        return cartProducts.toList()
    }

    fun deleteCartProduct(id: Int) {
        cartProducts.removeAt(id)
    }
}
