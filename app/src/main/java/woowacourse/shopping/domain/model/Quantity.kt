package woowacourse.shopping.domain.model

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class Quantity(
    val value: Int,
) {
    init {
        require(value > 0) { "수량은 1 이상이어야 합니다." }
    }
}
