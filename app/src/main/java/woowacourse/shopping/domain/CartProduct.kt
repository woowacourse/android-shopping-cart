package woowacourse.shopping.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class CartProduct(
    val cartProductId: UUID = UUID.randomUUID(),
    val product: Product,
    val amount: Int = 1,
) : Parcelable {
    init {
        require(amount >= 0) { "수량은 0 이상이여야 합니다." }
    }

    fun addQuantity(requestedAmount: Int) = copy(amount = amount + requestedAmount)

    fun calculateTotalPrice() = product.price * amount

}
