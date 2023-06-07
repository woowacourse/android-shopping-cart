package woowacourse.shopping.domain

class Quantity() {
    var amount: Int = 0
        private set
    fun add() = amount++
    fun subtract() = amount--
}
