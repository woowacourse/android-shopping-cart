package woowacourse.shopping.view.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemSelectedProductBinding
import woowacourse.shopping.domain.Product

class SelectedProductViewHolder(
    private val binding: ItemSelectedProductBinding,
    eventListener: OnRemoveProductListener,
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var currentItem: Product

    init {
        binding.onRemoveClick = eventListener
    }

    fun bind(product: Product) {
        currentItem = product
        binding.product = product
    }

    companion object {
        fun from(
            parent: ViewGroup,
            eventListener: OnRemoveProductListener,
        ): SelectedProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemSelectedProductBinding.inflate(inflater, parent, false)
            return SelectedProductViewHolder(binding, eventListener)
        }
    }
}
