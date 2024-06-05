package woowacourse.shopping.presentation.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.shopping.domain.CartProduct

@Parcelize
data class UpdateUiModel(
    val updatedItems: MutableMap<Long, CartProduct> = mutableMapOf(),
) : Parcelable {
    fun add(
        id: Long,
        cartProduct: CartProduct,
    ) {
        updatedItems[id] = cartProduct
    }
}
