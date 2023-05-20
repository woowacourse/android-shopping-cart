package woowacourse.shopping.model

import androidx.databinding.ObservableBoolean

data class CartProductUIModel(
    val product: ProductUIModel,
    val count: Int,
    val isChecked: ObservableBoolean = ObservableBoolean(true),
)
