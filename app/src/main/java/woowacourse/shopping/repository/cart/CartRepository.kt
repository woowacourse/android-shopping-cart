package woowacourse.shopping.repository.cart

import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductWithQuantity
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface CartRepository {
    suspend fun getTotalProductQuantity(): Int

    suspend fun getProductQuantity(productId: Uuid): Int

    fun getCartProducts(): Flow<List<ProductWithQuantity>>

    suspend fun addProduct(
        product: Product,
        quantityToAdd: Int,
    )

    suspend fun deleteProduct(productId: Uuid)

    suspend fun decreaseProduct(
        productId: Uuid,
        quantityToRemove: Int,
    )
}
