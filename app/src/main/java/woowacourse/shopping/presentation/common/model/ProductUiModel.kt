package woowacourse.shopping.presentation.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductUiModel(
    val id: String,
    val name: String,
    val price: Long,
    val imageUrl: String,
) : Parcelable
