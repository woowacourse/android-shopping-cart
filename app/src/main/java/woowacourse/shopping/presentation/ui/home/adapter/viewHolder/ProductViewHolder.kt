package woowacourse.shopping.presentation.ui.home.adapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.presentation.ui.home.adapter.ClickListenerByViewType
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter.ProductsByView.Products

class ProductViewHolder(
    private val binding: ItemProductBinding,
    private val clickProduct: ClickListenerByViewType,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.clickListener = clickProduct
    }

    fun bind(data: Products) {
        binding.product = data.product
    }

    companion object {
        fun getView(parent: ViewGroup, layoutInflater: LayoutInflater): ItemProductBinding =
            ItemProductBinding.inflate(layoutInflater, parent, false)
    }
}
