package woowacourse.shopping.model

import woowacourse.shopping.data.InquiryHistoryEntity
import java.time.LocalDateTime

data class InquiryHistory(
    val productId: Long,
    val inquiryTime: LocalDateTime,
) {
    companion object {
        fun InquiryHistory.toEntity() =
            InquiryHistoryEntity(
                productId = productId,
                inquiryTime = inquiryTime,
            )
    }
}
