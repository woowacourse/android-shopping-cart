package woowacourse.shopping.feature.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ProductState(
    val imageUrl: String,
    val name: String,
    val price: Int
) : Parcelable
