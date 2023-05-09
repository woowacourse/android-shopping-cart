package woowacourse.shopping.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.databinding.ItemShoppingCartBinding
import woowacourse.shopping.productdetail.ProductUiModel

class ShoppingCartItemViewHolder(
    private val binding: ItemShoppingCartBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        productUiModel: ProductUiModel,
        onRemoveClicked: (Int) -> Unit
    ) {
        with(binding) {
            Glide.with(root.context)
                .load(productUiModel.imageUrl)
                .into(imageProduct)

            textProductName.text = productUiModel.name
            textProductPrice.text = productUiModel.price.toString()
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
