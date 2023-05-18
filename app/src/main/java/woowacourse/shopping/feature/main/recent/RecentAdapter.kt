package woowacourse.shopping.feature.main.recent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.model.RecentProductUiModel

class RecentAdapter(
    items: List<RecentProductUiModel>,
    private val onClick: (recentProduct: RecentProductUiModel) -> Unit
) : RecyclerView.Adapter<RecentViewHolder>() {

    private val _items = items.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemRecentProductBinding>(
            layoutInflater,
            R.layout.item_recent_product,
            parent,
            false
        )
        return RecentViewHolder(binding)
    }

    override fun getItemCount(): Int = _items.size

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        holder.bind(_items[position], onClick)
    }

    fun setItems(items: List<RecentProductUiModel>) {
        _items.clear()
        _items.addAll(items)
        notifyItemRangeChanged(0, items.size)
    }
}
