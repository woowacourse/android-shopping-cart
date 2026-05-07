package woowacourse.shopping.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.shopping.domain.util.CountUpdateType
import java.util.UUID

@Parcelize
class PurchaseProducts(
    val purchaseProducts: List<PurchaseProduct> = emptyList()
): Parcelable {
    fun add(purchaseProduct: PurchaseProduct) =
        if(findById(purchaseProduct.uuid) == null) PurchaseProducts(purchaseProducts + purchaseProduct)
        else updateCountWithUuid(purchaseProduct.uuid, CountUpdateType.INCREASE)

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

    fun totalCountOfSpecificPurchaseProduct(uuid: UUID): Int {
        val targetProduct = findById(uuid) ?: return 0
        return targetProduct.count
    }

    fun totalCount() = purchaseProducts.sumOf { it.count }

    fun isContain(id: UUID): Boolean = purchaseProducts.any { it.uuid == id }

    fun findById(uuid: UUID) = purchaseProducts.find { it.isSameUUID(uuid) }
}