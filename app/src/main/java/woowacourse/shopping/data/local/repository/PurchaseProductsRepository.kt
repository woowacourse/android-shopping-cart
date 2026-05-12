package woowacourse.shopping.data.local.repository

import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.data.local.entity.PurchaseProductEntity
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.PurchaseProduct

interface PurchaseProductsRepository {
    fun getAll(): Flow<List<PurchaseProductEntity>?>

    suspend fun insert(purchaseProduct: PurchaseProduct)

    suspend fun updateCount(
        id: String,
        delta: Int,
    )

    suspend fun deletePurchaseProduct(id: String)

    fun getProductCount(): Flow<Int>
}