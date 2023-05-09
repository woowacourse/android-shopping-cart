package woowacourse.shopping.domain

import kotlin.properties.Delegates

class Product(
    val imageUrl: String,
    val name: String,
    val price: Int,
) {
    var id: Long? by Delegates.vetoable(null) { _, old, new ->
        old == null && new != null
    }
}
