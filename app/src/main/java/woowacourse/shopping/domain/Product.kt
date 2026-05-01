package woowacourse.shopping.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Product(
    val uuid: UUID = UUID.randomUUID(),
    val imageUri: String,
    val name: String,
    val price: Int,
): Parcelable  {
    init {
        require(imageUri.isNotBlank()) { "imageUri는 빈 값이 될 수 없습니다." }
        require(name.isNotBlank()) { "name은 빈 값이 될 수 없습니다." }
        require(price > 0) { "가격은 0원 초과여야 합니다." }
    }
}
