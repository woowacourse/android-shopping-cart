package woowacourse.shopping.mapper

import com.shopping.domain.RecentProduct
import woowacourse.shopping.uimodel.RecentProductUIModel

fun RecentProduct.toUIModel(): RecentProductUIModel {
    return RecentProductUIModel(time, product.toUIModel())
}

fun RecentProductUIModel.toDomain(): RecentProduct {
    return RecentProduct(time, product.toDomain())
}
