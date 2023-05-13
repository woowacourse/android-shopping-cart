package woowacourse.shopping.feature.main.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemMainProductBinding

class MainProductAdapter(
    items: List<MainProductItemModel>
) : RecyclerView.Adapter<MainProductViewHolder>() {
    private val _items = items.toMutableList()
    val items: List<MainProductItemModel>
        get() = _items.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainProductViewHolder {
        return MainProductViewHolder.create(parent)
    }

    override fun getItemCount(): Int = _items.size

    override fun onBindViewHolder(holder: MainProductViewHolder, position: Int) {
        holder.bind(_items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE
    }

    fun addItems(newItems: List<MainProductItemModel>) {
        _items.addAll(newItems.toList())
        notifyDataSetChanged()
    }

    companion object {
        const val VIEW_TYPE = 222
    }
}
