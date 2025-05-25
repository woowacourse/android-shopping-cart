package woowacourse.shopping.data.product

import woowacourse.shopping.data.dummy.ProductData
import woowacourse.shopping.domain.model.Product

fun Long.toProduct(): Product = ProductData.products.find { it.id == this } ?: throw IllegalArgumentException()

fun Product.toId(): Long = ProductData.products.find { it == this }?.id ?: throw IllegalArgumentException()
