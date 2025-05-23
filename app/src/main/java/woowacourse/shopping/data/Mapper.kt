package woowacourse.shopping.data

import woowacourse.shopping.data.product.ProductEntity
import woowacourse.shopping.data.shoppingcart.CartItemEntity
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product

fun Product.toEntity(): ProductEntity = ProductEntity(id, name, price, 0, imageUrl)

fun ProductEntity.toDomain(): Product = Product(id, name, price, imageUrl)

fun CartItem.toEntity(): CartItemEntity = CartItemEntity(id, name, price, quantity, imageUrl)

fun CartItemEntity.toDomain(): CartItem = CartItem(id, name, price, quantity, imageUrl)

fun Product.toCartItem(): CartItem = CartItem(id, name, price, 0, imageUrl)

fun CartItem.toProduct(): Product = Product(id, name, price, imageUrl)
