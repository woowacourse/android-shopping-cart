package woowacourse.shopping.data

import woowacourse.shopping.data.history.HistoryEntity
import woowacourse.shopping.domain.model.History

fun HistoryEntity.toDomain(): History = History(id = id, name = name, thumbnailUrl = thumbnailUrl)

fun History.toEntity(): HistoryEntity = HistoryEntity(id = id, name = name, thumbnailUrl = thumbnailUrl)
