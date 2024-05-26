package woowacourse.shopping.model

import woowacourse.shopping.data.inquiryhistory.InquiryHistoryEntity
import java.time.LocalDateTime

data class InquiryHistory(
    val product: Product,
    val inquiryTime: LocalDateTime,
) {
    companion object {
        fun InquiryHistory.toEntity() =
            InquiryHistoryEntity(
                product = product,
                inquiryTime = inquiryTime,
            )
    }
}
