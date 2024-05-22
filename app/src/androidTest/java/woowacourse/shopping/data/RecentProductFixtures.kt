package woowacourse.shopping.data

import woowacourse.shopping.data.db.entity.RecentProductEntity
import java.time.LocalDateTime

private const val DEFAULT_ID = 1L
private val DEFAULT_DATE_TIME = LocalDateTime.of(2024, 5, 22, 2, 58, 23)

fun recentProductEntity(
    id: Long = DEFAULT_ID,
    createdTime: LocalDateTime = DEFAULT_DATE_TIME,
): RecentProductEntity {
    return RecentProductEntity(id, createdTime)
}