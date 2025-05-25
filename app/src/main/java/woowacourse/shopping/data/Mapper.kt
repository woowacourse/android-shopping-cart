package woowacourse.shopping.data

import woowacourse.shopping.data.product.ProductEntity
import woowacourse.shopping.data.recent.RecentProductEntity
import woowacourse.shopping.data.shoppingcart.CartItemEntity
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.view.inventory.item.InventoryItem.InventoryProduct

fun Product.toEntity(): ProductEntity = ProductEntity(id, name, price, 0, imageUrl)

fun InventoryProduct.toEntity(): ProductEntity = ProductEntity(id, name, price, quantity, imageUrl)

fun ProductEntity.toDomain(): InventoryProduct = InventoryProduct(id, name, price, quantity, imageUrl)

fun CartProduct.toEntity(): CartItemEntity = CartItemEntity(id, name, price, quantity, imageUrl)

fun CartItemEntity.toDomain(): CartProduct = CartProduct(id, name, price, quantity, imageUrl)

fun CartProduct.toInventoryProduct(): InventoryProduct = InventoryProduct(id, name, price, quantity, imageUrl)

fun InventoryProduct.toCartItem(): CartProduct = CartProduct(id, name, price, quantity, imageUrl)

fun RecentProduct.toEntity(): RecentProductEntity = RecentProductEntity(id, name, imageUrl, timestamp)
