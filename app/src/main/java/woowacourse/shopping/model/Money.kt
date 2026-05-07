package woowacourse.shopping.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@JvmInline
value class Money(
    val value: Int,
) {
    init {
        require(value >= 0) { "금액은 0 이상이어야 합니다." }
    }
}
