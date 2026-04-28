package woowacourse.shopping.domain

class Money(private val amount: Int) {
    init {
        require(amount >= 0) { "가격은 0원 이상이어야 합니다." }
    }
}
