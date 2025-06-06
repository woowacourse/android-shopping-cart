package woowacourse.shopping.data

import woowacourse.shopping.data.product.ProductEntity
import woowacourse.shopping.data.recent.RecentProductEntity
import woowacourse.shopping.data.shoppingcart.CartItemEntity
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentItem
import woowacourse.shopping.view.inventory.adapter.InventoryItem.ProductUiModel

fun Product.toEntity(): ProductEntity = ProductEntity(id, name, price, 0, imageUrl)

fun Product.toUiModel(): ProductUiModel = ProductUiModel(this, 0)

fun ProductEntity.toDomain(): Product = Product(id, name, price, imageUrl)

fun CartProduct.toEntity(): CartItemEntity = CartItemEntity(id, name, price, quantity, imageUrl)

fun CartItemEntity.toDomain(): CartProduct = CartProduct(id, name, price, quantity, imageUrl)

fun CartProduct.toUiModel(): ProductUiModel = ProductUiModel(Product(id, name, price, imageUrl), quantity)

fun ProductUiModel.toCartItem(): CartProduct = CartProduct(product.id, product.name, product.price, quantity, product.imageUrl)

fun RecentItem.toEntity(): RecentProductEntity = RecentProductEntity(id, name, imageUrl, timestamp)
