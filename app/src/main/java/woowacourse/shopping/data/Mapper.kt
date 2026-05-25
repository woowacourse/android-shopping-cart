package woowacourse.shopping.data

import woowacourse.shopping.data.db.CartItemWithProduct
import woowacourse.shopping.data.db.RecentProductWithProduct
import woowacourse.shopping.data.entity.CatalogEntity
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product

fun CatalogEntity.toDomain(): Product = Product(
    productId = productId,
    name = name,
    price = price,
    imageUri = imageUri
)

fun Product.toEntity(): CatalogEntity = CatalogEntity(
    productId = productId,
    name = name,
    price = price,
    imageUri = imageUri
)

fun CartItemWithProduct.toDomain(): CartProduct = CartProduct(
    cartProductId = this.catalogEntity.productId,
    product = this.catalogEntity.toDomain(),
    amount = this.cartEntity.amount
)

fun RecentProductWithProduct.toDomain(): Product = this.catalogEntity.toDomain()
