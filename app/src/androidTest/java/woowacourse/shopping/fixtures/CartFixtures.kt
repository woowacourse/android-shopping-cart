package woowacourse.shopping.fixtures

import woowacourse.shopping.data.db.entity.CartEntity

private const val DEFAULT_ID = 1L
private const val DEFAULT_COUNT = 0

fun cartEntity(
    id: Long = DEFAULT_ID,
    count: Int = DEFAULT_COUNT,
): CartEntity {
    return CartEntity(id, count)
}

fun cartEntities(
    vararg ids: Long
): List<CartEntity> {
    return ids.map { cartEntity(it) }
}