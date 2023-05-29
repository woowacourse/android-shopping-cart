package woowacourse.shopping.ui.model.mapper

import woowacourse.shopping.domain.ShoppingProduct
import woowacourse.shopping.ui.model.ShoppingProductModel
import woowacourse.shopping.ui.model.mapper.ProductMapper.toDomain
import woowacourse.shopping.ui.model.mapper.ProductMapper.toView

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
