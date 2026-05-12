package woowacourse.shopping.data.local.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import woowacourse.shopping.data.local.dao.PurchaseProductsDao
import woowacourse.shopping.data.local.entity.PurchaseProductEntity
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.PurchaseProduct
import woowacourse.shopping.domain.PurchaseProducts

class PurchaseProductsRepository(
    private val purchaseProductsDao: PurchaseProductsDao,
) {
    fun getAll() = purchaseProductsDao.getAll()

    suspend fun insert(purchaseProduct: PurchaseProduct) {
        val entityItem = purchaseProduct.toEntity()
        purchaseProductsDao.upsert(entityItem)
    }

    fun getProductCount() = purchaseProductsDao.getProductCount()


    suspend fun updateCount(
        id: String,
        delta: Int,
    ) {
        purchaseProductsDao.updateCount(id, delta)
    }

    suspend fun deletePurchaseProduct(id: String) {
        purchaseProductsDao.deleteWithId(id)
    }
}

private fun PurchaseProduct.toEntity(): PurchaseProductEntity {
    val product = this.product
    return PurchaseProductEntity(
        id = product.id,
        count = count,
    )
}
