package woowacourse.shopping.common.model.mapper

import woowacourse.shopping.common.model.RecentProductModel
import woowacourse.shopping.common.model.mapper.ProductMapper.toDomainModel
import woowacourse.shopping.common.model.mapper.ProductMapper.toViewModel
import woowacourse.shopping.domain.RecentProduct

object RecentProductMapper : Mapper<RecentProduct, RecentProductModel> {
    override fun RecentProduct.toViewModel(): RecentProductModel {
        return RecentProductModel(
            ordinal,
            product.toViewModel()
        )
    }

    override fun RecentProductModel.toDomainModel(): RecentProduct {
        return RecentProduct(
            ordinal,
            product.toDomainModel()
        )
    }
}
