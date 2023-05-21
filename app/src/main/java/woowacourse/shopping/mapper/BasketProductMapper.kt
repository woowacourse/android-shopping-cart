package woowacourse.shopping.mapper

import woowacourse.shopping.domain.model.DomainCartProduct
import woowacourse.shopping.model.UiCartProduct

fun UiCartProduct.toDomain(): DomainCartProduct = DomainCartProduct(
    id = id,
    product = product.toDomain(),
    selectedCount = selectedCount.toDomain(),
    isChecked = isChecked,
)

fun DomainCartProduct.toUi(): UiCartProduct = UiCartProduct(
    id = id,
    product = product.toUi(),
    selectedCount = selectedCount.toUi(),
    isChecked = isChecked,
)

fun List<DomainCartProduct>.toUi(): List<UiCartProduct> =
    map { cartProduct -> cartProduct.toUi() }
