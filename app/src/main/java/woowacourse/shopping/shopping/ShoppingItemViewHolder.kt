package woowacourse.shopping.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemShoppingProductBinding
import woowacourse.shopping.model.ProductUiModel

class ShoppingItemViewHolder(
    private val binding: ItemShoppingProductBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(productUiModel: ProductUiModel, onClicked: (ProductUiModel) -> Unit) {
        with(binding) {
            product = productUiModel
            root.setOnClickListener { onClicked(productUiModel) }
        }
    }

    companion object {
        const val PRODUCT_ITEM_TYPE = 0

        fun from(parent: ViewGroup): ShoppingItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemShoppingProductBinding.inflate(layoutInflater, parent, false)

            return ShoppingItemViewHolder(binding)
        }
    }
}
