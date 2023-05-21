package woowacourse.shopping.feature.model.mapper

import com.example.domain.RecentProducts
import woowacourse.shopping.feature.list.item.ProductView

fun RecentProducts.toItem(): ProductView.RecentProductsItem {
    return ProductView.RecentProductsItem(this.products.map { product -> product.toUi().toItem() })
}
