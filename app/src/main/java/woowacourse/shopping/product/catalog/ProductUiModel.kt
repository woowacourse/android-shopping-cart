package woowacourse.shopping.product.catalog

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductUiModel(
    val imageUrl: String,
    val name: String,
    val price: Int,
) : Parcelable
