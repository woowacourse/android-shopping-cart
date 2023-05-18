package woowacourse.shopping.model

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt

data class CartProductUIModel(
    val product: ProductUIModel,
    val count: ObservableInt = ObservableInt(1),
    val isChecked: ObservableBoolean = ObservableBoolean(true),
)
