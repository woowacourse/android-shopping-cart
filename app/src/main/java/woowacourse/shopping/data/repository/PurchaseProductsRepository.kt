package woowacourse.shopping.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import woowacourse.shopping.data.dao.PurchaseProductsDao
import woowacourse.shopping.data.entity.PurchaseProductEntity
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.PurchaseProduct
import woowacourse.shopping.domain.PurchaseProducts
import java.util.UUID

class PurchaseProductsRepository(private val purchaseProductsDao: PurchaseProductsDao) {
    fun getCart(): Flow<Cart> {
        return purchaseProductsDao.getAll()
            .map {
                val domainItems = it.map { it.toPurchaseProductObject() }
                Cart(PurchaseProducts(domainItems))
            }
    }

    suspend fun insert(purchaseProduct: PurchaseProduct) {
        val entityItem = purchaseProduct.toEntity()
        purchaseProductsDao.insertAll(entityItem)
    }

    fun findWithId(id: UUID): Flow<PurchaseProduct> {
        return purchaseProductsDao.findWithId(id).map { it.toPurchaseProductObject() }
    }

    fun getTotalPriceOfSpecificPurchaseProduct(id: UUID) =
        purchaseProductsDao.getTotalPriceOfSpecificPurchaseProduct(id)

    fun getTotalAmount() = purchaseProductsDao.getTotalAmount()

    fun getCountOfSpecificPurchaseProduct(id: UUID) =
        purchaseProductsDao.getCountOfSpecificPurchaseProduct(id)

    fun isContained(id: UUID) = purchaseProductsDao.isContained(id)

    fun getProductCount() =
        purchaseProductsDao.getProductCount()

    fun partedProducts(page: Int, pageSize: Int):Flow<Cart> {
        val offset = page * pageSize
        return purchaseProductsDao.getPartedPurchaseProducts(pageSize, offset).map {
            val domainItems = it.map { it.toPurchaseProductObject() }
            Cart(PurchaseProducts(domainItems))
        }
    }

    suspend fun updateCount(id: UUID, delta: Int) {
        purchaseProductsDao.updateCount(id, delta)
    }

    suspend fun deletePurchaseProduct(id: UUID) {
        purchaseProductsDao.deleteWithId(id)
    }
}

private fun PurchaseProduct.toEntity(): PurchaseProductEntity {
    val product = this.product
    return PurchaseProductEntity(
        id = product.uuid,
        name = product.name,
        price = product.price,
        imageUri = product.imageUri,
        count = count
    )
}
