package woowacourse.shopping.ui.shopping.recyclerview.adapter.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.model.UiBasketProduct
import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.util.listener.ProductClickListener
import woowacourse.shopping.widget.ProductCounterView.OnClickListener

class ProductViewHolder(
    parent: ViewGroup,
    productClickListener: ProductClickListener,
    counterClickListener: OnClickListener,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
) {
    private val binding = ItemProductBinding.bind(itemView)

    init {
        binding.productClickListener = productClickListener
        binding.counterClickListener = counterClickListener
    }

    fun bind(basketProduct: UiBasketProduct) {
        binding.basketProduct = basketProduct
    }
}
