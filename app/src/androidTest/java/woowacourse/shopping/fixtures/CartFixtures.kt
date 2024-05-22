package woowacourse.shopping.fixtures

import woowacourse.shopping.data.db.entity.CartEntity

private const val DEFAULT_ID = 0L
private const val DEFAULT_COUNT = 0

fun cartEntity(
    id: Long = DEFAULT_ID,
    count: Int = DEFAULT_COUNT,
): CartEntity {
    return CartEntity(id, count)
}