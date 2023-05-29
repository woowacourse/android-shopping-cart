package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.entity.ProductEntity
import woowacourse.shopping.domain.model.Product

object ProductMapper {

    fun ProductEntity.toDomainModel(): Product = Product(
        id = this.id,
        name = this.name,
        itemImage = this.itemImage,
        price = this.price,
    )
}
