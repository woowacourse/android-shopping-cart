package woowacourse.shopping.feature.main.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemMainProductBinding
import woowacourse.shopping.model.ProductUiModel

class MainProductAdapter(
    items: List<ProductUiModel>,
    private val mainProductClickListener: MainProductClickListener
) : RecyclerView.Adapter<MainProductViewHolder>() {
    private val _items = items.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemMainProductBinding>(
            layoutInflater,
            R.layout.item_main_product,
            parent,
            false
        )
        return MainProductViewHolder(binding, mainProductClickListener)
    }

    override fun getItemCount(): Int = _items.size

    override fun onBindViewHolder(holder: MainProductViewHolder, position: Int) {
        holder.bind(_items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE
    }

    fun addItems(newItems: List<ProductUiModel>) {
        val previousSize = _items.size
        _items.addAll(newItems.toList())
        notifyItemRangeInserted(previousSize, newItems.size)
    }

    companion object {
        const val VIEW_TYPE = 222
    }
}
