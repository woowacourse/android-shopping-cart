package woowacourse.shopping.common.model.mapper

import woowacourse.shopping.common.model.CartOrdinalProductModel
import woowacourse.shopping.common.model.mapper.CartProductMapper.toDomainModel
import woowacourse.shopping.common.model.mapper.CartProductMapper.toViewModel
import woowacourse.shopping.domain.CartOrdinalProduct

object CartOrdinalProductMapper : Mapper<CartOrdinalProduct, CartOrdinalProductModel> {
    override fun CartOrdinalProduct.toViewModel(): CartOrdinalProductModel {
        return CartOrdinalProductModel(
            ordinal,
            cartProduct.toViewModel()
        )
    }

    override fun CartOrdinalProductModel.toDomainModel(): CartOrdinalProduct {
        return CartOrdinalProduct(
            ordinal,
            cartProduct.toDomainModel()
        )
    }
}
