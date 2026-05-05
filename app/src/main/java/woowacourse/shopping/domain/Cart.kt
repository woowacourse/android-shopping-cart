package woowacourse.shopping.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
class Cart(
    val products: Products = Products(),
) : Parcelable {
    fun addProduct(product: Product): Cart {
        val product = products.add(product)
        return Cart(product)
    }

    fun removeProduct(id: UUID): Cart {
        val product = products.remove(id)
        return Cart(product)
    }

    fun size() = products.size()
}
