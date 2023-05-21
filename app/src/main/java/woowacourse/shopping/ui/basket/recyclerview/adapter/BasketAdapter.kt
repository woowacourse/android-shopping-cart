package woowacourse.shopping.ui.basket.recyclerview.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.model.UiBasketProduct
import woowacourse.shopping.ui.basket.listener.CartClickListener
import woowacourse.shopping.util.diffutil.BasketDiffUtil

class BasketAdapter(
    private val cartClickListener: CartClickListener,
) : ListAdapter<UiBasketProduct, BasketViewHolder>(BasketDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder =
        BasketViewHolder(parent, cartClickListener)

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
