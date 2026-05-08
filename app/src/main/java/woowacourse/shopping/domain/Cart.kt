package woowacourse.shopping.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
class Cart(
    val purchaseProducts: PurchaseProducts = PurchaseProducts(),
): Parcelable {
    fun add(purchaseProduct: PurchaseProduct) = Cart(purchaseProducts.add(purchaseProduct))

    fun updateCountWithId(
        uuid: UUID,
        updateAmount: Int
    ) = Cart(purchaseProducts.updateCountWithUuid(uuid, updateAmount))

    fun removeWithId(uuid: UUID) = Cart(purchaseProducts.removeProduct(uuid))

    fun totalPriceOfSpecificPurchaseProduct(uuid: UUID) = purchaseProducts.totalPriceOfSpecificPurchaseProduct(uuid)

    fun totalCountOfPurchaseProducts() = purchaseProducts.totalCount()

    fun totalCountOfSpecificPurchaseProduct(uuid: UUID) = purchaseProducts.totalCountOfSpecificPurchaseProduct(uuid)

    fun isContain(id: UUID) = purchaseProducts.isContain(id)

    fun findById(uuid: UUID) = purchaseProducts.findById(uuid)
}
