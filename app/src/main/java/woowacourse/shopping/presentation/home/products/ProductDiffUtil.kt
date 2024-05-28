package woowacourse.shopping.presentation.home.products

import androidx.recyclerview.widget.DiffUtil
import woowacourse.shopping.data.model.cart.CartItem
import woowacourse.shopping.data.model.history.RecentProduct
import woowacourse.shopping.data.model.product.CartableProduct

object ProductDiffUtil : DiffUtil.ItemCallback<CartableProduct>() {
    override fun areItemsTheSame(oldItem: CartableProduct, newItem: CartableProduct): Boolean {
        return oldItem.product.id == newItem.product.id
    }

    override fun areContentsTheSame(oldItem: CartableProduct, newItem: CartableProduct): Boolean {
        return oldItem == newItem
    }
}
