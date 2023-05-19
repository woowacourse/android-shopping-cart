package woowacourse.shopping.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductModel(
    val id: Long,
    val title: String,
    val price: Int,
    val imageUrl: String,
) : Parcelable
