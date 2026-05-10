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
    fun getCart(): Flow<Cart> =
        purchaseProductsDao
            .getAll()
            .map {
                val domainItems = it.map { it.toPurchaseProductObject() }
                Cart(PurchaseProducts(domainItems))
            }

    suspend fun insert(purchaseProduct: PurchaseProduct) {
        val entityItem = purchaseProduct.toEntity()
        purchaseProductsDao.upsert(entityItem)
    }

    fun findWithId(id: String): Flow<PurchaseProduct> = purchaseProductsDao.findWithId(id).map { it.toPurchaseProductObject() }

    fun getTotalPriceOfSpecificPurchaseProduct(id: String) = purchaseProductsDao.getTotalPriceOfSpecificPurchaseProduct(id)

    fun getTotalAmount() = purchaseProductsDao.getTotalAmount()

    fun getCountOfSpecificPurchaseProduct(id: String) = purchaseProductsDao.getCountOfSpecificPurchaseProduct(id)

    fun isContained(id: String) = purchaseProductsDao.isContained(id)

    fun getProductCount() = purchaseProductsDao.getProductCount()

    fun partedProducts(
        page: Int,
        pageSize: Int,
    ): Flow<Cart> {
        val offset = page * pageSize
        return purchaseProductsDao.getPartedPurchaseProducts(pageSize, offset).map {
            val domainItems = it.map { it.toPurchaseProductObject() }
            Cart(PurchaseProducts(domainItems))
        }
    }

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
        name = product.name,
        price = product.price,
        imageUri = product.imageUri,
        count = count,
    )
}
