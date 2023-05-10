package woowacourse.shopping.feature.cart

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.shopping.model.CartProductUiModel

@Parcelize
data class CartProductItemModel(
    val cartProduct: CartProductUiModel,
    val onClick: (position: Int) -> Unit
) : Parcelable
