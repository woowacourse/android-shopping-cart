package woowacourse.shopping.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
class Products(
    val products: List<Product> = emptyList(),
): Parcelable {
    fun size() = products.size

    fun add(product: Product) = Products(products + product)

    fun remove(id: UUID): Products {
        val product = findWithId(id) ?: return this
        return Products(products - product)
    }

    fun findWithId(id: UUID) = products.find { it.uuid == id }
}
