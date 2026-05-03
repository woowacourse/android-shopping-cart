package woowacourse.shopping.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductUiModel(
    val id: String,
    val name: String,
    val price: Int,
    val imageUrl: String,
): Parcelable