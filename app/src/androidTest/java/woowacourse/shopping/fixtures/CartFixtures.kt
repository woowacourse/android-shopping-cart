package woowacourse.shopping.fixtures

import woowacourse.shopping.local.entity.CartEntity

private const val DEFAULT_ID = 1L
private const val DEFAULT_COUNT = 0

fun fakeCartEntity(
    id: Long = DEFAULT_ID,
    count: Int = DEFAULT_COUNT,
): CartEntity {
    return CartEntity(id, count)
}

fun fakeCartEntities(vararg ids: Long): List<CartEntity> {
    return ids.map { fakeCartEntity(it) }
}
