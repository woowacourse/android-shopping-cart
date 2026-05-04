package woowacourse.shopping.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
@JvmInline
value class ProductId(
    val value: UUID,
) : Parcelable
