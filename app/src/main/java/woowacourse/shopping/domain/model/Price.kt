package woowacourse.shopping.domain.model

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class Price(
    val value: Int,
) {
    init {
        require(value >= 0) { "가격은 음수일 수 없습니다." }
    }
}
