package woowacourse.shopping.presentation.view.catalog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductContainerBinding
import woowacourse.shopping.presentation.ui.decorations.HorizontalEdgeSpacingDecoration
import woowacourse.shopping.presentation.view.catalog.adapter.model.CatalogItem

class RecentProductContainerViewHolder private constructor(
    binding: ItemRecentProductContainerBinding,
    eventListener: CatalogAdapter.CatalogEventListener,
) : RecyclerView.ViewHolder(binding.root) {
    private val adapter = RecentProductAdapter(emptyList(), eventListener)

    init {
        binding.recyclerViewRecentProduct.adapter = adapter
        binding.recyclerViewRecentProduct.addItemDecoration(HorizontalEdgeSpacingDecoration())
    }

    fun bind(recentProducts: CatalogItem.RecentProducts) {
        adapter.updateList(recentProducts.products)
    }

    companion object {
        fun from(
            parent: ViewGroup,
            eventListener: CatalogAdapter.CatalogEventListener,
        ): RecentProductContainerViewHolder {
            val binding =
                ItemRecentProductContainerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
            return RecentProductContainerViewHolder(binding, eventListener)
        }
    }
}
