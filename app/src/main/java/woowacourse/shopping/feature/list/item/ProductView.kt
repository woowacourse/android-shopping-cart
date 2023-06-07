package woowacourse.shopping.feature.list.item

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface ProductView {

    @Parcelize
    data class CartProductItem(
        val id: Int,
        val imageUrl: String,
        val name: String,
        val price: Int,
        var count: Int = DEFAULT_COUNT,
    ) : ProductView, Parcelable {

        fun updateCount(count: Int): CartProductItem {
            this.count = count
            return this
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as CartProductItem

            if (id != other.id) return false
            if (imageUrl != other.imageUrl) return false
            if (name != other.name) return false
            if (price != other.price) return false

            return true
        }

        override fun hashCode(): Int {
            var result = id
            result = 31 * result + imageUrl.hashCode()
            result = 31 * result + name.hashCode()
            result = 31 * result + price
            return result
        }

        companion object {
            private const val DEFAULT_COUNT = 1
        }
    }

    data class RecentProductsItem(
        val products: List<CartProductItem>,
    ) : ProductView

    companion object {
        const val TYPE_CART_PRODUCT = 0
        const val TYPE_RECENT_PRODUCTS = 1
    }
}
