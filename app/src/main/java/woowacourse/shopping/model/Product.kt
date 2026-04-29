package woowacourse.shopping.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Product(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val price: Money,
    val imageUrl: String
): Parcelable
