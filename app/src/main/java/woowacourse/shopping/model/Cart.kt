package woowacourse.shopping.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cart(
    val items: Map<Product, Int>
): Parcelable
