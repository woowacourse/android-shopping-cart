package woowacourse.shopping.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.shopping.domain.util.CountUpdateType
import java.util.UUID

@Parcelize
class Cart(
    val purchaseProducts: PurchaseProducts = PurchaseProducts(),
): Parcelable {
    fun add(purchaseProduct: PurchaseProduct) = Cart(purchaseProducts.add(purchaseProduct))

    fun updateCountWithId(
        uuid: UUID,
        updateType: CountUpdateType
    ) = Cart(purchaseProducts.updateCountWithUuid(uuid, updateType))

    fun removeWithId(uuid: UUID) = Cart(purchaseProducts.removeProduct(uuid))

    fun totalPriceOfSpecificPurchaseProduct(uuid: UUID) = purchaseProducts.totalPriceOfSpecificPurchaseProduct(uuid)

    fun totalCountOfPurchaseProducts() = purchaseProducts.totalCount()

    fun findById(uuid: UUID) = purchaseProducts.findById(uuid)
}
