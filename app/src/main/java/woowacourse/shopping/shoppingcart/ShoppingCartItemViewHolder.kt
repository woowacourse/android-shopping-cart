package woowacourse.shopping.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.databinding.ItemShoppingCartBinding
import woowacourse.shopping.model.ShoppingCartProductUiModel

class ShoppingCartItemViewHolder private constructor(
    private val binding: ItemShoppingCartBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        product: ShoppingCartProductUiModel,
        onRemoveClicked: (Int) -> Unit
    ) {
        with(binding) {
            Glide.with(root.context)
                .load(product.imageUrl)
                .into(imageProduct)

            textProductName.text = product.name
            textProductPrice.text = product.price.toString()
            imageRemoveProduct.setOnClickListener { onRemoveClicked(adapterPosition) }
        }
    }

    companion object {
        fun from(parent: ViewGroup): ShoppingCartItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemShoppingCartBinding.inflate(layoutInflater, parent, false)

            return ShoppingCartItemViewHolder(binding)
        }
    }
}
