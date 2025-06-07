package woowacourse.shopping.data

import woowacourse.shopping.data.product.ProductEntity
import woowacourse.shopping.data.recent.RecentProductEntity
import woowacourse.shopping.data.shoppingcart.CartItemEntity
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentItem
import woowacourse.shopping.view.inventory.adapter.InventoryItem.ProductUiModel

fun Product.toEntity(): ProductEntity = ProductEntity(id, name, price, 0, imageUrl)

fun CartProduct.toEntity(): CartItemEntity = CartItemEntity(id, name, price, quantity, imageUrl)

fun RecentItem.toEntity(): RecentProductEntity = RecentProductEntity(id, name, imageUrl, timestamp)

fun Product.toUiModel(): ProductUiModel = ProductUiModel(this, 0)

fun CartProduct.toUiModel(): ProductUiModel = ProductUiModel(Product(id, name, price, imageUrl), quantity)

fun ProductEntity.toProduct(): Product = Product(id, name, price, imageUrl)

fun CartItemEntity.toCartProduct(): CartProduct = CartProduct(id, name, price, quantity, imageUrl)

fun ProductUiModel.toCartProduct(): CartProduct = CartProduct(product.id, product.name, product.price, quantity, product.imageUrl)
