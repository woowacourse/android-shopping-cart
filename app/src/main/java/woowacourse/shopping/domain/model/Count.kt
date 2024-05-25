package woowacourse.shopping.domain.model

data class Count(var amount: Int = 2) {
    fun increase() {
        amount += 1
    }

    fun decrease() {
        if (amount > 1) {
            amount -= 1
        }
    }
}
