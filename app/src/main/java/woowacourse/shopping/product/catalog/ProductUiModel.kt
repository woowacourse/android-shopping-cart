package woowacourse.shopping.product.catalog

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductUiModel(
    val id: Long,
    val imageUrl: String,
    val name: String,
    val price: Int,
    val quantity: Int = 0,
    val isExpanded: Boolean = false,
) : Parcelable
