package woowacourse.shopping.domain

import java.time.LocalDateTime

data class RecentProduct(val time: LocalDateTime, val product: Product) {
    fun updateTime(): RecentProduct {
        return copy(time = LocalDateTime.now())
    }
}
