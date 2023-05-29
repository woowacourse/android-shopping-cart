package woowacourse.shopping.ui.model.mapper

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.URL
import woowacourse.shopping.ui.model.ProductModel

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
