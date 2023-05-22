package woowacourse.shopping.presentation.ui.home.adapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentlyViewedProductBinding
import woowacourse.shopping.domain.model.RecentlyViewedProduct
import woowacourse.shopping.presentation.ui.home.adapter.ProductClickListener

class RecentlyViewedItemViewHolder(
    private val binding: ItemRecentlyViewedProductBinding,
    productClickListener: ProductClickListener,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.listener = productClickListener
    }

    fun bind(data: RecentlyViewedProduct) {
        binding.product = data
    }

    companion object {
        fun getView(parent: ViewGroup): ItemRecentlyViewedProductBinding {
            val layoutInflater = LayoutInflater.from(parent.context)
            return ItemRecentlyViewedProductBinding.inflate(
                layoutInflater,
                parent,
                false,
            )
        }
    }
}
