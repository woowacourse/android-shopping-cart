package woowacourse.shopping.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
class CartProducts(
    private val products: List<CartProduct> = emptyList(),
) : Parcelable {
    val items: List<CartProduct>
        get() = products.toList()

    val uniqueItemCount = products.size
    val totalQuantity = products.sumOf { it.amount }

    fun addQuantityOfCartProduct(product: Product, plusAmount: Int = 1): CartProducts {
        require(plusAmount > 0) { "추가할 수량은 1 이상이어야 합니다." }

        val existingCartProduct = findSameProduct(product.productId)

        val updatedProducts = if (existingCartProduct != null) {
            products.map {
                if (it.product.productId == product.productId) it.addQuantity(plusAmount) else it
            }
        } else {
            products + CartProduct(product = product, amount = plusAmount)
        }

        return CartProducts(updatedProducts)
    }

    fun decreaseQuantityOfCartProduct(productId: UUID, minusAmount: Int = 1): CartProducts {
        require(minusAmount > 0) { "감소할 수량은 1 이상이어야 합니다." }

        if (findSameProduct(productId) == null) return this

        val updatedProducts = products.map { cartProduct ->
            if (cartProduct.product.productId == productId) {
                cartProduct.decreaseQuantity(minusAmount)
            } else {
                cartProduct
            }
        }

        return CartProducts(updatedProducts)
    }


    fun remove(productId: UUID): CartProducts {
        val product = findSameProduct(productId) ?: return this
        return CartProducts(products - product)
    }

    fun calculateTotalPrice(): Long {
        var totalPrice = 0L
        for (product in products) {
            totalPrice += product.calculateTotalPrice()
        }

        return totalPrice
    }

    private fun findOrCreateCartProduct(product: Product): CartProduct =
        findSameProduct(product.productId) ?: CartProduct(product = product, amount = 0)

    fun findSameProduct(id: UUID) = products.find { it.product.productId == id }
}
