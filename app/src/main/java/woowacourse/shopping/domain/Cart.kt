package woowacourse.shopping.domain

import java.util.Collections.emptyList
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Cart(
    val productAndCounts: List<ProductAndCount> = emptyList(),
) {
    val _productAndCounts = productAndCounts.toMutableList()
    fun addProductToCart(product: Product): Cart {
        val index = productAndCounts.indexOfFirst { product.productId == it.product.productId }
        if (index!=-1) {
            val existProductAndCount = productAndCounts[index]
            val newProductAndCount = existProductAndCount.increaseQuantity()
            val updated = productAndCounts.toMutableList()
            updated[index] = newProductAndCount
            return copy(productAndCounts = updated)
        } else {
            return copy(productAndCounts = productAndCounts + ProductAndCount(product, 1))
        }
    }

    fun deleteProductFromCart(productId: Uuid): Cart =
        copy(
            productAndCounts =
                productAndCounts.filterNot {
                    it.productId == productId
                },
        )
}
