package woowacourse.shopping.model.uimodel

import com.shopping.domain.Count

class CartProductUIModel(
    val productUIModel: ProductUIModel,
    val count: Count,
    val isSelected: Boolean
)
