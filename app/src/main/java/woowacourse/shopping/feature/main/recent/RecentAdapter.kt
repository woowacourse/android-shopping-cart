package woowacourse.shopping.feature.main.recent

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecentAdapter(
    items: List<RecentProductItemModel>
) : RecyclerView.Adapter<RecentViewHolder>() {
    private val _items = items.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        return RecentViewHolder.create(parent)
    }

    override fun getItemCount(): Int = _items.size

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        holder.bind(_items[position])
    }

    fun setItems(items: List<RecentProductItemModel>) {
        _items.clear()
        _items.addAll(items)
        notifyDataSetChanged()
    }
}
