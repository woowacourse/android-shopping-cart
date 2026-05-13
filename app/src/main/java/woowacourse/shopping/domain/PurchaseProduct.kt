package woowacourse.shopping.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PurchaseProduct(
    val product: Product,
    val count: Int = 1,
) : Parcelable {
    init {
        require(count > 0) { "구매할 상품의 개수는 1개 이상이어야 합니다." }
    }

    fun updateCount(updateAmount: Int): PurchaseProduct {
        val newCount = count + updateAmount
        return copy(count = newCount)
    }

    fun name() = product.name

    fun price() = product.price

    fun imageUri() = product.imageUri

    fun id() = product.id

    fun totalPrice() = product.price * count

    fun isSameID(id: String) = id == product.id
}
