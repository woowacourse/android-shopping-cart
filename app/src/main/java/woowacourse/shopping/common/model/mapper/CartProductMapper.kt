package woowacourse.shopping.common.model.mapper

import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.common.model.mapper.ProductMapper.toDomainModel
import woowacourse.shopping.common.model.mapper.ProductMapper.toViewModel
import woowacourse.shopping.domain.CartProduct

object CartProductMapper : Mapper<CartProduct, CartProductModel> {
    override fun CartProduct.toViewModel(): CartProductModel {
        return CartProductModel(
            amount, product.toViewModel()
        )
    }

    override fun CartProductModel.toDomainModel(): CartProduct {
        return CartProduct(
            amount, product.toDomainModel()
        )
    }
}
