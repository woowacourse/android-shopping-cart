package woowacourse.shopping.domain.model

import java.io.Serializable

data class Name(val value: String) : Serializable {
    init {
        require(value.isNotEmpty()) { ERROR_NAME_EMPTY }
    }

    companion object {
        private const val ERROR_NAME_EMPTY: String = "상품 이름이 존재하지 않습니다"
    }
}
