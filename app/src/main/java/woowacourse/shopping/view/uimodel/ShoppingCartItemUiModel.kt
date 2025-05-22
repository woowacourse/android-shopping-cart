package woowacourse.shopping.view.uimodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShoppingCartItemUiModel(
    val id: Long = 0,
    val productUiModel: ProductUiModel,
    val quantity: Int,
) : Parcelable
