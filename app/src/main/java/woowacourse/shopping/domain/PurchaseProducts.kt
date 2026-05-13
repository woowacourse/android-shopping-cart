package woowacourse.shopping.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class PurchaseProducts(
    val purchaseProducts: List<PurchaseProduct> = emptyList(),
) : Parcelable {
    fun add(purchaseProduct: PurchaseProduct) =
        if (findById(purchaseProduct.id()) == null) {
            PurchaseProducts(purchaseProducts + purchaseProduct)
        } else {
            updateCountWithUuid(purchaseProduct.id(), purchaseProduct.count)
        }

    fun updateCountWithUuid(
        id: String,
        updateAmount: Int,
    ) = PurchaseProducts(
        purchaseProducts.map {
            if (it.isSameID(id)) it.updateCount(updateAmount) else it
        },
    )

    fun removeProduct(id: String): PurchaseProducts {
        val targetPurchaseProduct = findById(id) ?: return this
        return PurchaseProducts(purchaseProducts - targetPurchaseProduct)
    }

    fun totalPriceOfSpecificPurchaseProduct(id: String): Int {
        val targetProduct = findById(id) ?: return 0
        return targetProduct.totalPrice()
    }

    fun totalCountOfSpecificPurchaseProduct(id: String): Int {
        val targetProduct = findById(id) ?: return 0
        return targetProduct.count
    }

    fun totalCount() = purchaseProducts.sumOf { it.count }

    fun isContain(id: String): Boolean = purchaseProducts.any { it.id() == id }

    fun findById(id: String) = purchaseProducts.find { it.isSameID(id) }
}
