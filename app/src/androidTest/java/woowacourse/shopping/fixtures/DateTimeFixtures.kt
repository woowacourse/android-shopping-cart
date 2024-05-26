package woowacourse.shopping.fixtures

import java.time.LocalDateTime

fun dateTime(
    year: Int = 2024,
    month: Int = 5,
    dayOfMonth: Int = 22,
    hour: Int = 2,
    minute: Int = 58,
    second: Int = 23,
): LocalDateTime {
    return LocalDateTime.of(year, month, dayOfMonth, hour, minute, second)
}
