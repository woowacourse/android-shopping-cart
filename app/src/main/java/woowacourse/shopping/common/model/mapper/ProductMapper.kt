package woowacourse.shopping.common.model.mapper

import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.URL

object ProductMapper : Mapper<Product, ProductModel> {
    override fun Product.toView(): ProductModel {
        return ProductModel(
            picture.value,
            title,
            price
        )
    }

    override fun ProductModel.toDomain(): Product {
        return Product(
            URL(picture),
            title,
            price
        )
    }
}
