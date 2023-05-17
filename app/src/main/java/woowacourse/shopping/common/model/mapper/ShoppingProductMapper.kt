package woowacourse.shopping.common.model.mapper

import woowacourse.shopping.common.model.ShoppingProductModel
import woowacourse.shopping.common.model.mapper.ProductMapper.toDomain
import woowacourse.shopping.common.model.mapper.ProductMapper.toView
import woowacourse.shopping.domain.ShoppingProduct

object ShoppingProductMapper : Mapper<ShoppingProduct, ShoppingProductModel> {
    override fun ShoppingProduct.toView(): ShoppingProductModel {
        return ShoppingProductModel(
            product.toView(),
            amount
        )
    }

    override fun ShoppingProductModel.toDomain(): ShoppingProduct {
        return ShoppingProduct(
            product.toDomain(),
            amount
        )
    }
}
