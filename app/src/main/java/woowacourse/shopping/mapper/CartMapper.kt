package woowacourse.shopping.mapper

import com.shopping.domain.Cart
import woowacourse.shopping.CartUIModel

fun Cart.toUIModel(): CartUIModel = CartUIModel(products.map { it.toUIModel() })

fun CartUIModel.toDomain(): Cart = Cart(products.map { it.toDomain() })
