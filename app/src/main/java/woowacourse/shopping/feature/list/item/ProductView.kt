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
