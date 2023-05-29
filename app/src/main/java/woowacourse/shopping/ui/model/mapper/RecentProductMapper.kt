package woowacourse.shopping.ui.model.mapper

import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.ui.model.RecentProductModel
import woowacourse.shopping.ui.model.mapper.ProductMapper.toDomain
import woowacourse.shopping.ui.model.mapper.ProductMapper.toView

object RecentProductMapper : Mapper<RecentProduct, RecentProductModel> {
    override fun RecentProduct.toView(): RecentProductModel {
        return RecentProductModel(
            time,
            product.toView()
        )
    }

    override fun RecentProductModel.toDomain(): RecentProduct {
        return RecentProduct(
            time,
            product.toDomain()
        )
    }
}
