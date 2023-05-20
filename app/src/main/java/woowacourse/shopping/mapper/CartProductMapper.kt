package woowacourse.shopping.mapper

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import com.example.domain.model.CartProduct
import woowacourse.shopping.model.CartProductUIModel

fun CartProduct.toUIModel(): CartProductUIModel {
    return CartProductUIModel(
        product = this.product.toUIModel(),
        count = this.count,
        isChecked = ObservableBoolean(this.isChecked),
    )
}

fun CartProductUIModel.toDomain(): CartProduct {
    return CartProduct(
        product = this.product.toDomain(),
        count = this.count,
        isChecked = this.isChecked.get(),
    )
}
