package woowacourse.shopping.mapper

import woowacourse.shopping.domain.DomainBasketProduct
import woowacourse.shopping.model.UiBasketProduct

fun UiBasketProduct.toDomain(): DomainBasketProduct = DomainBasketProduct(
    id = id,
    product = product.toDomain(),
    selectedCount = selectedCount.toDomain(),
    isChecked = isChecked,
)

fun DomainBasketProduct.toUi(): UiBasketProduct = UiBasketProduct(
    id = id,
    product = product.toUi(),
    selectedCount = selectedCount.toUi(),
    isChecked = isChecked,
)
