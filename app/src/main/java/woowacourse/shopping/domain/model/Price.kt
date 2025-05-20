package woowacourse.shopping.domain.model

import java.io.Serializable

data class Price(val value: Int) : Serializable {
    init {
        require(value > MINIMUM_PRICE) { ERROR_MINIMUM_PRICE }
    }

    companion object {
        private const val MINIMUM_PRICE: Int = 0

        private const val ERROR_MINIMUM_PRICE: String = "가격은 0원 이하일 수 없습니다"
    }
}
