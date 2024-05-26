package woowacourse.shopping.data

import woowacourse.shopping.data.InquiryHistoryEntity.Companion.toDomainModel
import woowacourse.shopping.model.InquiryHistory
import woowacourse.shopping.model.InquiryHistory.Companion.toEntity

class InquiryHistoryLocalRepository(private val dao: InquiryHistoryDao) : InquiryHistoryRepository {
    override fun save(inquiryHistory: InquiryHistory) {
        dao.insert(inquiryHistory.toEntity())
    }

    override fun findAll(): List<InquiryHistory> = dao.getAll().map { inquiryHistoryEntity -> inquiryHistoryEntity.toDomainModel() }
}
