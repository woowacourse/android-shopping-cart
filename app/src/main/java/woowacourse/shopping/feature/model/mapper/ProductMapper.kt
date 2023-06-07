package woowacourse.shopping.feature.model.mapper

import com.example.domain.Product
import woowacourse.shopping.feature.list.item.ProductView

fun Product.toCartUi(): ProductView.CartProductItem {
    return ProductView.CartProductItem(id, imageUrl, name, price)
}
