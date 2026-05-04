package woowacourse.shopping.domain

import java.util.Collections.emptyList
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

val CART_PAGE_SIZE = 5

@OptIn(ExperimentalUuidApi::class)
data class Cart(
    val productAndCounts: List<ProductAndCount> = emptyList(),
) {
    fun addProductToCart(product: Product): Cart {
        val index = productAndCounts.indexOfFirst { product.productId == it.product.productId }
        if (index != -1) {
            val newProductAndCounts = productAndCounts.map { item ->
                if (item.product == product) {
                    item.copy(count = item.count() + 1)
                } else {
                    item
                }
            }
            return copy(productAndCounts = newProductAndCounts)
        }
        return copy(productAndCounts = productAndCounts + ProductAndCount(product, 1))
    }


    fun deleteProductFromCart(productId: Uuid): Cart =
        copy(
            productAndCounts =
                productAndCounts.filterNot {
                    it.productId == productId
                },
        )

    fun getProductAndCounts(
        page: Int,
        pageSize: Int = CART_PAGE_SIZE,
    ): List<ProductAndCount> {
        val fromIndex = maxOf(1, page * pageSize)
        val toIndex = minOf(fromIndex + pageSize, productAndCounts.size)
        return productAndCounts.subList(fromIndex, toIndex)
    }
}
