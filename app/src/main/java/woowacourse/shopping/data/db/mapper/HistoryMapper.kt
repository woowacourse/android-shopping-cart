package woowacourse.shopping.data.db.mapper

import woowacourse.shopping.data.db.model.HistoryEntity
import woowacourse.shopping.domain.model.History

fun HistoryEntity.toHistory(): History = History(product.toProduct(), timestamp)

fun History.toEntity(): HistoryEntity = HistoryEntity(product = product.toEntity(), timestamp = timestamp)

fun List<HistoryEntity>.toHistory(): List<History> =
    this.map {
        it.toHistory()
    }

fun List<History>.toEntity(): List<HistoryEntity> =
    this.map {
        it.toEntity()
    }
