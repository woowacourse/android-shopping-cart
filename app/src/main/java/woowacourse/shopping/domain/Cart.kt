package woowacourse.shopping.domain

import java.util.Collections.emptyList
import kotlin.math.min
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

val CART_PAGE_SIZE = 5

@OptIn(ExperimentalUuidApi::class)
data class Cart(
    val productAndCounts: List<ProductAndCount> = emptyList(),
) {
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

    fun getProductAndCounts(page:Int, pageSize:Int = CART_PAGE_SIZE):List<ProductAndCount>{
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + CART_PAGE_SIZE, productAndCounts.size)
        return productAndCounts.subList(fromIndex,toIndex)
    }
}


