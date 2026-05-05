package woowacourse.shopping.presentation.productdetail.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Parcelize
@OptIn(ExperimentalUuidApi::class)
data class ProductUiModel(
    val productId: Uuid,
    val imageUrl: String,
    val productName: String,
    val price: Int,
) : Parcelable
