package woowacourse.shopping.common.model.mapper

import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.URL

object ProductMapper : Mapper<Product, ProductModel> {
    override fun Product.toViewModel(): ProductModel {
        return ProductModel(
            id,
            picture.value,
            title,
            price
        )
    }

    override fun ProductModel.toDomainModel(): Product {
        return Product(
            id,
            URL(picture),
            title,
            price
        )
    }
}
