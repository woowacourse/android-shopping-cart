package woowacourse.shopping.mapper

import com.example.domain.model.Product
import woowacourse.shopping.model.ProductUIModel

fun Product.toUIModel(): ProductUIModel {
    return ProductUIModel(this.id, this.name, this.price, this.imageUrl)
}

fun ProductUIModel.toDomain(): Product {
    return Product(this.id, this.name, this.price, this.imageUrl)
}
