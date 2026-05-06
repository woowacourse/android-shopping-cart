package woowacourse.shopping.domain

import woowacourse.shopping.domain.util.CountUpdateType
import java.util.UUID

class PurchaseProducts(
    val purchaseProducts: List<PurchaseProduct> = emptyList()
) {
    fun add(purchaseProduct: PurchaseProduct) = PurchaseProducts(purchaseProducts + purchaseProduct)

    fun updateCountWithUuid(uuid: UUID, updateType: CountUpdateType) = PurchaseProducts (
        purchaseProducts.map {
            if(it.isSameUUID(uuid)) it.updateCount(updateType) else it
        }
    )

    fun removeProduct(uuid: UUID): PurchaseProducts {
        val targetPurchaseProduct = findById(uuid) ?: return this
        return PurchaseProducts(purchaseProducts - targetPurchaseProduct)
    }

    fun totalPriceOfSpecificPurchaseProduct(uuid: UUID): Int {
        val targetProduct = findById(uuid) ?: return 0
        return targetProduct.totalPrice()
    }

    fun totalCount() = purchaseProducts.sumOf { it.count }

    fun findById(uuid: UUID) = purchaseProducts.find { it.isSameUUID(uuid) }
}