package woowacourse.shopping.features.productDetail

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ParcelProduct(
    val id: String,
    val name: String,
    val price: Int,
    val imageUrl: String,
) : Parcelable
