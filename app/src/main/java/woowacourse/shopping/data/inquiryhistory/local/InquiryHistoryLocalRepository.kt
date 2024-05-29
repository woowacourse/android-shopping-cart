package woowacourse.shopping.data.inquiryhistory.local

import woowacourse.shopping.data.inquiryhistory.InquiryHistoryRepository
import woowacourse.shopping.data.inquiryhistory.local.InquiryHistoryEntity.Companion.toDomainModel
import woowacourse.shopping.model.InquiryHistory
import woowacourse.shopping.model.InquiryHistory.Companion.toEntity

class InquiryHistoryLocalRepository(private val dao: InquiryHistoryDao) : InquiryHistoryRepository {
    override fun save(inquiryHistory: InquiryHistory) {
        dao.insert(inquiryHistory.toEntity())
    }

    override fun findAll(): List<InquiryHistory> {
        val sortedInquiryHistories = dao.getAll().sortedByDescending { it.inquiryTime }
        val inquiryHistories = sortedInquiryHistories.distinctBy { it.product.id }.take(10)

        return inquiryHistories.map { inquiryHistoryEntity -> inquiryHistoryEntity.toDomainModel() }
    }

    override fun findLastViewedProduct(): InquiryHistory? {
        val sortedInquiryHistories = dao.getAll().sortedByDescending { it.inquiryTime }
        if (sortedInquiryHistories.isNotEmpty()) {
            return sortedInquiryHistories.first().toDomainModel()
        }
        return null
    }
}
