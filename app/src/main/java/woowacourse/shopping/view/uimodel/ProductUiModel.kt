package woowacourse.shopping.view.uimodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductUiModel(
    val id: Long,
    val name: String,
    val price: Int,
    val imageUrl: String,
) : Parcelable, QuantityObservable
