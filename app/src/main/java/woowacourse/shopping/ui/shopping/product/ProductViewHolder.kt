package woowacourse.shopping.ui.shopping.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.ui.model.UiProduct
import woowacourse.shopping.util.setThrottleFirstOnClickListener

class ProductViewHolder(
    parent: ViewGroup,
    onItemClick: (Int) -> Unit,
    minusClickListener: (UiProduct) -> Unit,
    plusClickListener: (UiProduct) -> Unit
) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
    ) {
    private val binding = ItemProductBinding.bind(itemView)

    init {
        binding.root.setThrottleFirstOnClickListener { onItemClick(bindingAdapterPosition) }
        binding.counterProduct.minusClickListener = minusClickListener
        binding.counterProduct.plusClickListener = plusClickListener
        binding.plusClickListener = plusClickListener
    }

    fun bind(product: UiProduct) {
        binding.product = product
        binding.counterProduct.product = product
        binding.counterProduct.count = product.basketCount
        binding.hasBasketProduct = product.basketCount != 0
    }
}
