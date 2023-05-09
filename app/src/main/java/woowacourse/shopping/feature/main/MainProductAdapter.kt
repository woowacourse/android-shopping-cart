package woowacourse.shopping.feature.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemMainProductBinding
import woowacourse.shopping.model.ProductUiModel

class MainProductAdapter(
    items: List<MainProductItemModel>
) : RecyclerView.Adapter<MainProductViewHolder>() {
    private val _items = items.toMutableList()
    val items: List<MainProductItemModel>
        get() = _items.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemMainProductBinding>(
            layoutInflater,
            R.layout.item_main_product,
            parent,
            false
        )
        return MainProductViewHolder(binding)
    }

    override fun getItemCount(): Int = _items.size

    override fun onBindViewHolder(holder: MainProductViewHolder, position: Int) {
        holder.bind(_items[position])
    }

    fun addItems(newItems: List<MainProductItemModel>) {
        _items.addAll(newItems.toList())
        notifyDataSetChanged()
    }
}