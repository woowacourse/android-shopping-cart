package woowacourse.shopping.repository

import androidx.compose.runtime.mutableStateListOf
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductAndCount
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

object CartRepository {
    private val products = mutableStateListOf<ProductAndCount>()

    fun getProducts(): List<ProductAndCount> = products

    @OptIn(ExperimentalUuidApi::class)
    fun addProduct(product: Product) {
        val index = products.indexOfFirst { product.productId == it.product.productId }
        if (index!=-1) {
            val existProductAndCount = products[index]
            products[index] = existProductAndCount.increaseQuantity()
        } else {
            products.add(
                ProductAndCount(product, 1)
            )
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    fun deleteProduct(productId: Uuid) {
        products.remove(products.find { it.productId == productId })
    }
}
