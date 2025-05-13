package woowacourse.shopping.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val name: String,
    val imageUrl: String,
    val price: Int,
) : Parcelable
