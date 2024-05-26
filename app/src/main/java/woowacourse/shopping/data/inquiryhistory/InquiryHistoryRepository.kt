package woowacourse.shopping.data.inquiryhistory

import woowacourse.shopping.model.InquiryHistory

interface InquiryHistoryRepository {
    fun save(inquiryHistory: InquiryHistory)

    fun findAll(): List<InquiryHistory>
}
