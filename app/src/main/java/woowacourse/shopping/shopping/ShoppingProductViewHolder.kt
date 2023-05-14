package woowacourse.shopping.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import woowacourse.shopping.databinding.ItemShoppingProductBinding
import woowacourse.shopping.model.ProductUiModel

class ShoppingProductViewHolder private constructor(
    binding: ItemShoppingProductBinding,
) : ShoppingRecyclerItemViewHolder<ShoppingRecyclerItem.ShoppingProduct, ItemShoppingProductBinding>(
    binding
) {

    private lateinit var product: ShoppingRecyclerItem.ShoppingProduct

    fun setOnClicked(onClicked: (ProductUiModel) -> Unit) {
        binding.root.setOnClickListener { onClicked(product.value) }
    }

    override fun bind(itemData: ShoppingRecyclerItem.ShoppingProduct) {
        product = itemData

        with(binding) {
            Glide.with(binding.root.context)
                .load(product.value.imageUrl)
                .into(binding.imageProduct)

            textProductName.text = product.value.name
            textProductPrice.text = product.value.price.toString()
        }
    }

    companion object {
        fun from(parent: ViewGroup): ShoppingProductViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemShoppingProductBinding.inflate(layoutInflater, parent, false)

            return ShoppingProductViewHolder(binding)
        }
    }
}
