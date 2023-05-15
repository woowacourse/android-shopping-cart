package woowacourse.shopping.common.model.mapper

import woowacourse.shopping.common.model.RecentProductModel
import woowacourse.shopping.common.model.mapper.ProductMapper.toDomain
import woowacourse.shopping.common.model.mapper.ProductMapper.toView
import woowacourse.shopping.domain.RecentProduct

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
