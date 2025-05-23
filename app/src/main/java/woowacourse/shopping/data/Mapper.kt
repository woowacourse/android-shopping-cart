package woowacourse.shopping.data

import woowacourse.shopping.data.product.ProductEntity
import woowacourse.shopping.data.shoppingcart.CartItemEntity
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.inventory.item.InventoryItem.InventoryProduct

fun Product.toEntity(): ProductEntity = ProductEntity(id, name, price, 0, imageUrl)

fun InventoryProduct.toEntity(): ProductEntity = ProductEntity(id, name, price, quantity, imageUrl)

fun ProductEntity.toDomain(): InventoryProduct = InventoryProduct(id, name, price, quantity, imageUrl)

fun CartItem.toEntity(): CartItemEntity = CartItemEntity(id, name, price, quantity, imageUrl)

fun CartItemEntity.toDomain(): CartItem = CartItem(id, name, price, quantity, imageUrl)

fun Product.toCartItem(): CartItem = CartItem(id, name, price, 0, imageUrl)

fun CartItem.toInventoryProduct(): InventoryProduct = InventoryProduct(id, name, price, quantity, imageUrl)
