package woowacourse.shopping.common.model.mapper

import woowacourse.shopping.common.model.CheckableCartProductModel
import woowacourse.shopping.common.model.mapper.CartProductMapper.toDomainModel
import woowacourse.shopping.common.model.mapper.CartProductMapper.toViewModel
import woowacourse.shopping.domain.CheckableCartProduct

object CheckableCartProductMapper : Mapper<CheckableCartProduct, CheckableCartProductModel> {
    override fun CheckableCartProduct.toViewModel(): CheckableCartProductModel {
        return CheckableCartProductModel(
            checked, product.toViewModel()
        )
    }

    override fun CheckableCartProductModel.toDomainModel(): CheckableCartProduct {
        return CheckableCartProduct(
            checked, cartProduct.toDomainModel()
        )
    }
}
