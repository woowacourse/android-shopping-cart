package woowacourse.shopping.ui.shopping.recyclerview.adapter.recentproduct

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.model.UiRecentProduct
import woowacourse.shopping.util.extension.setOnSingleClickListener

class RecentProductViewHolder(parent: ViewGroup, onItemClick: (Int) -> Unit) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_recent_product, parent, false)
    ) {
    private val binding = ItemRecentProductBinding.bind(itemView)

    init {
        binding.root.setOnSingleClickListener { onItemClick(bindingAdapterPosition) }
    }

    fun bind(recentProduct: UiRecentProduct) {
        binding.product = recentProduct.product
    }
}
