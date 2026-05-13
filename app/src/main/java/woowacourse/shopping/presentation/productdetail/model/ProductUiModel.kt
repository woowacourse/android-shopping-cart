package woowacourse.shopping.presentation.productdetail.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.uuid.ExperimentalUuidApi

@Parcelize
@OptIn(ExperimentalUuidApi::class)
data class ProductUiModel(
    val productId: Int,
    val imageUrl: String,
    val productName: String,
    val price: Int,
) : Parcelable
