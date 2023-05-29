package woowacourse.shopping.ui.model.mapper

import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.ui.model.CartProductModel
import woowacourse.shopping.ui.model.mapper.ProductMapper.toDomain
import woowacourse.shopping.ui.model.mapper.ProductMapper.toView

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
