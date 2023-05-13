package woowacourse.shopping.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.databinding.ItemShoppingProductBinding
import woowacourse.shopping.model.ProductUiModel

class ShoppingItemViewHolder(
    private val binding: ItemShoppingProductBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(productUiModel: ProductUiModel, onClicked: (ProductUiModel) -> Unit) {
        with(binding) {
            Glide.with(binding.root.context)
                .load(productUiModel.imageUrl)
                .into(imageProduct)

            textProductName.text = productUiModel.name
            textProductPrice.text = productUiModel.price.toString()
            root.setOnClickListener { onClicked(productUiModel) }
        }
    }

    companion object {

        fun from(parent: ViewGroup): ShoppingItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemShoppingProductBinding.inflate(layoutInflater, parent, false)

            return ShoppingItemViewHolder(binding)
        }
    }
}
