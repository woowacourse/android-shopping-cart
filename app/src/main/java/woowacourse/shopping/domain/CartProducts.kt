package woowacourse.shopping.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
class CartProducts(
    val products: List<Product> = emptyList(),
) : Parcelable {
    val items: List<Product>
        get() = products.toList()

    fun size() = products.size

    fun add(product: Product) = CartProducts(products + product)

    fun remove(id: UUID): CartProducts {
        val product = findWithId(id) ?: return this
        return CartProducts(products - product)
    }

    fun findWithId(id: UUID) = products.find { it.uuid == id }
}
