package woowacourse.shopping.data

import woowacourse.shopping.data.db.CartEntity
import woowacourse.shopping.domain.model.Product

object CartMapper {
//    fun CartEntity.toDomain(): Product =
//        Product(
//            productId = this.productId,
//            name = this.name,
//            imageUrl = this.imageUrl,
//            _price = Price(this.price),
//        )

    fun Product.toEntity(): CartEntity =
        CartEntity(
            productId = this.productId,
            quantity = 2,
        )
}
