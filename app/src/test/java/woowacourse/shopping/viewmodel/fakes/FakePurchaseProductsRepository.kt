package woowacourse.shopping.viewmodel.fakes

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import woowacourse.shopping.data.local.entity.PurchaseProductEntity
import woowacourse.shopping.data.local.repository.PurchaseProductsRepository
import woowacourse.shopping.domain.PurchaseProduct

class FakePurchaseProductsRepository : PurchaseProductsRepository {
    private val _db = MutableStateFlow<Map<String, Int>>(emptyMap())

    override fun getAll(): Flow<List<PurchaseProductEntity>?> =
        _db.map { it.map { (id, count) -> PurchaseProductEntity(id, count) } }

    override suspend fun insert(purchaseProduct: PurchaseProduct) {
        val current = _db.value.toMutableMap()
        current[purchaseProduct.id()] = (current[purchaseProduct.id()] ?: 0) + purchaseProduct.count
        _db.value = current
    }

    override suspend fun updateCount(
        id: String,
        delta: Int,
    ) {
        val current = _db.value.toMutableMap()
        val currentCount = current[id] ?: return
        val newCount = currentCount + delta

        if (newCount <= 0) {
            current.remove(id)
        } else {
            current[id] = newCount
        }
        _db.value = current
    }

    override suspend fun deletePurchaseProduct(id: String) {
        val current = _db.value.toMutableMap()
        current.remove(id)
        _db.value = current
    }



    override fun getProductCount(): Flow<Int> = _db.map { it.size }
}
