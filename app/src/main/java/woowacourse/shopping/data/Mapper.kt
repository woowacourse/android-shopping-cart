package woowacourse.shopping.data

import woowacourse.shopping.data.product.ProductEntity
import woowacourse.shopping.data.recent.RecentProductEntity
import woowacourse.shopping.data.shoppingcart.CartItemEntity
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.view.inventory.item.InventoryItem.ProductItem

fun Product.toEntity(): ProductEntity = ProductEntity(id, name, price, 0, imageUrl)

fun ProductItem.toEntity(): ProductEntity = ProductEntity(product.id, product.name, product.price, quantity, product.imageUrl)

fun ProductEntity.toDomain(): ProductItem = ProductItem(Product(id, name, price, imageUrl), quantity)

fun CartProduct.toEntity(): CartItemEntity = CartItemEntity(id, name, price, quantity, imageUrl)

fun CartItemEntity.toDomain(): CartProduct = CartProduct(id, name, price, quantity, imageUrl)

fun CartProduct.toDomain(): ProductItem = ProductItem(Product(id, name, price, imageUrl), quantity)

fun ProductItem.toCartItem(): CartProduct = CartProduct(product.id, product.name, product.price, quantity, product.imageUrl)

fun RecentProduct.toEntity(): RecentProductEntity = RecentProductEntity(id, name, imageUrl, timestamp)
