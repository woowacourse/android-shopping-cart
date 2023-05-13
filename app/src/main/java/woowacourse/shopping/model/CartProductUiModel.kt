package woowacourse.shopping.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.shopping.feature.cart.CartProductItemModel

@Parcelize
data class CartProductUiModel(
    val cartId: Long,
    val productUiModel: ProductUiModel
) : Parcelable {
    fun toItemModel(onClick: (cartId: Long) -> Unit): CartProductItemModel {
        return CartProductItemModel(this, onClick)
    }
}
