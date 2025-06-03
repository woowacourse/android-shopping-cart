package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.db.CartEntity
import woowacourse.shopping.data.db.RecentProductEntity
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Price
import woowacourse.shopping.domain.model.Product

fun CartEntity.toProduct() = Product(id, name, imageUrl, Price(price))

fun CartEntity.toCartItem() = CartItem(toProduct(), amount)

fun CartItem.toCartEntity() = CartEntity(product.id, product.name, product.imageUrl, product.price.value, amount)

fun Product.toRecentEntity() = RecentProductEntity(productId = id)
