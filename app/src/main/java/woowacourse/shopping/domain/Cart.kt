package woowacourse.shopping.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Cart(
    val purchaseProducts: PurchaseProducts = PurchaseProducts(),
) : Parcelable {
    fun add(purchaseProduct: PurchaseProduct) = Cart(purchaseProducts.add(purchaseProduct))

    fun updateCountWithId(
        id: String,
        updateAmount: Int,
    ) = Cart(purchaseProducts.updateCountWithUuid(id, updateAmount))

    fun removeWithId(id: String) = Cart(purchaseProducts.removeProduct(id))

    fun totalPriceOfSpecificPurchaseProduct(id: String) = purchaseProducts.totalPriceOfSpecificPurchaseProduct(id)

    fun totalCountOfPurchaseProducts() = purchaseProducts.totalCount()

    fun totalCountOfSpecificPurchaseProduct(id: String) = purchaseProducts.totalCountOfSpecificPurchaseProduct(id)

    fun isContain(id: String) = purchaseProducts.isContain(id)

    fun findById(id: String) = purchaseProducts.findById(id)
}
