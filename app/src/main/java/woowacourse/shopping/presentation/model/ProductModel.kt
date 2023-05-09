package woowacourse.shopping.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductModel(
    val imageUrl: String,
    val name: String,
    val price: Int,
) : Parcelable
