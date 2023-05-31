package woowacourse.shopping.model.uimodel.mapper

import com.shopping.domain.CartProduct
import com.shopping.domain.Product
import com.shopping.domain.RecentProduct
import woowacourse.shopping.model.uimodel.CartProductUIModel
import woowacourse.shopping.model.uimodel.ProductUIModel
import woowacourse.shopping.model.uimodel.RecentProductUIModel

fun ProductUIModel.toDomain() =
    Product(id, name, url, price)

fun Product.toUIModel() =
    ProductUIModel(id, name, url, price)

fun RecentProductUIModel.toDomain() =
    RecentProduct(productUIModel.toDomain())

fun RecentProduct.toUIModel() =
    RecentProductUIModel(product.toUIModel())

fun CartProductUIModel.toDomain() =
    CartProduct(productUIModel.toDomain(), count, isSelected)

fun CartProduct.toUIModel() =
    CartProductUIModel(product.toUIModel(), count, isSelected)

fun List<ProductUIModel>.toDomain() = this.map { Product(it.id, it.name, it.url, it.price) }

fun List<Product>.toUIModel() = this.map { ProductUIModel(it.id, it.name, it.url, it.price) }
