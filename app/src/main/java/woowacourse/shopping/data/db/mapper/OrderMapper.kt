package woowacourse.shopping.data.db.mapper

import woowacourse.shopping.data.db.model.OrderEntity
import woowacourse.shopping.domain.model.Order

fun Order.toEntity(): OrderEntity = OrderEntity(orderId = id, quantity = quantity, product = product.toEntity())

fun OrderEntity.toOrder(): Order = Order(orderId, quantity, product.toProduct())

fun List<Order>.toEntity(): List<OrderEntity> = map { it.toEntity() }

fun List<OrderEntity>.toOrder(): List<Order> = map { it.toOrder() }
