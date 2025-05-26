package woowacourse.shopping.data.product

import woowacourse.shopping.data.dummy.ProductData
import woowacourse.shopping.domain.model.Product
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun Long.toProduct(): Product = ProductData.products.find { it.id == this } ?: throw IllegalArgumentException()

fun Long.toLocalDateTime(): LocalDateTime = Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDateTime()

fun LocalDateTime.toEpochMillis(): Long = this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
