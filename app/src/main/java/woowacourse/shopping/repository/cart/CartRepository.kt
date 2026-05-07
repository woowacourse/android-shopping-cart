package woowacourse.shopping.repository.cart

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductWithQuantity
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface CartRepository {
    fun getTotalProductQuantity(): Int
    fun getProductQuantity(productId: Uuid): Int
    fun getCartProducts(): List<ProductWithQuantity>
    fun addProduct(product: Product, quantityToAdd: Int)
    fun deleteProduct(productId: Uuid)
    fun decreaseProduct(productId: Uuid, quantityToRemove: Int)
}