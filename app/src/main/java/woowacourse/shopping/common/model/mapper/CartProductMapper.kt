package woowacourse.shopping.common.model.mapper

import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.common.model.mapper.ProductMapper.toDomain
import woowacourse.shopping.common.model.mapper.ProductMapper.toView
import woowacourse.shopping.domain.CartProduct

object CartProductMapper : Mapper<CartProduct, CartProductModel> {
    override fun CartProduct.toView(): CartProductModel {
        return CartProductModel(
            time,
            amount,
            isChecked,
            product.toView()
        )
    }

    override fun CartProductModel.toDomain(): CartProduct {
        return CartProduct(
            time,
            amount,
            isChecked,
            product.toDomain()
        )
    }
}
