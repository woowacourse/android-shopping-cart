package woowacourse.shopping.presentation.ui.home.adapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentlyViewedBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.ui.home.adapter.RecentlyViewedProductAdapter

class RecentlyViewedViewHolder(
    private val binding: ItemRecentlyViewedBinding,
    private val clickProduct: (productId: Long) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {
    private val adapter = RecentlyViewedProductAdapter(clickProduct)

    init {
        binding.rvWrapper.adapter = adapter
    }

    fun bind(data: List<Product>) {
        adapter.initItems(data)
    }

    companion object {
        fun getView(parent: ViewGroup): ItemRecentlyViewedBinding {
            val layoutInflater = LayoutInflater.from(parent.context)
            return ItemRecentlyViewedBinding.inflate(layoutInflater, parent, false)
        }
    }
}
