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
        val targetCartProduct = findOrCreateCartProduct(product)
        val newCarProduct = targetCartProduct.addQuantity(plusAmount)

        return CartProducts(products + newCarProduct)
    }

    fun remove(productId: UUID): CartProducts {
        val product = findSameProduct(productId) ?: return this
        return CartProducts(products - product)
    }

    private fun findOrCreateCartProduct(product: Product): CartProduct =
        findSameProduct(product.productId) ?: CartProduct(product = product, amount = 0)

    fun findSameProduct(id: UUID) = products.find { it.product.productId == id }
}
