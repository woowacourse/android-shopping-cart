package woowacourse.shopping.data.product

import woowacourse.shopping.domain.model.Product

object ProductMapper {

    fun ProductEntity.toDomainModel(): Product = Product(
        id = this.id,
        name = this.name,
        itemImage = this.itemImage,
        price = this.price,
    )
}
