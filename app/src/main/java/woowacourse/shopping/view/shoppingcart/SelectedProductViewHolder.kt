package woowacourse.shopping.view.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemSelectedProductBinding
import woowacourse.shopping.domain.Product

class SelectedProductViewHolder(
    private val binding: ItemSelectedProductBinding,
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var currentItem: Product

    fun bind(product: Product) {}

    companion object {
        fun from(parent: ViewGroup): SelectedProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemSelectedProductBinding.inflate(inflater, parent, false)
            return SelectedProductViewHolder(binding)
        }
    }
}
