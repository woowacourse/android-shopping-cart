package woowacourse.shopping.view.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductMoreBinding

class ProductMoreViewHolder(
    private val binding: ItemProductMoreBinding,
    load: () -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.load = load
    }

    fun bind(item: ProductsItem.LoadItem) {
        binding.loadItem = item
    }

    companion object {
        fun of(
            parent: ViewGroup,
            load: () -> Unit,
        ): ProductMoreViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemProductMoreBinding.inflate(layoutInflater, parent, false)
            return ProductMoreViewHolder(binding, load)
        }
    }
}
