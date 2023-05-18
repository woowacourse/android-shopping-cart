package woowacourse.shopping.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import woowacourse.shopping.databinding.ItemShoppingProductBinding
import woowacourse.shopping.model.ProductUiModel

class ShoppingProductViewHolder private constructor(
    binding: ItemShoppingProductBinding,
) : ShoppingRecyclerItemViewHolder<ShoppingRecyclerItem.ShoppingProduct, ItemShoppingProductBinding>(
    binding
) {

    private lateinit var product: ShoppingRecyclerItem.ShoppingProduct

    fun setOnClicked(
        onProductImageClicked: (ProductUiModel) -> Unit,
        productCountPickerListener: ShoppingProductCountPicker,
    ) {
        with(binding) {
            imageProduct.setOnClickListener {
                onProductImageClicked(product.value)
            }
            imageAddToCart.setOnClickListener {
                it.isVisible = false
                layoutSelectProductCount.isVisible = true
                productCountPickerListener.onAdded(product.value)
            }
            buttonPlusProductCount.setOnClickListener {
                textProductCount.plusCount()
                productCountPickerListener.onPlus(product.value)
            }
            buttonMinusProductCount.setOnClickListener {
                textProductCount.minusCount()
                productCountPickerListener.onMinus(product.value)
            }
        }
    }

    override fun bind(itemData: ShoppingRecyclerItem.ShoppingProduct) {
        product = itemData

        with(binding) {
            Glide.with(binding.root.context)
                .load(product.value.imageUrl)
                .into(binding.imageProduct)

            textProductName.text = product.value.name
        }
    }

    private fun TextView.plusCount() {
        val count = (text.toString().toInt() + 1).toString()

        text = count
    }

    private fun TextView.minusCount() {
        val count = text.toString().toInt() - 1

        if (count == 0) {
            binding.layoutSelectProductCount.isVisible = false
            binding.imageAddToCart.isVisible = true
            return
        }
        text = count.toString()
    }

    companion object {
        fun from(parent: ViewGroup): ShoppingProductViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemShoppingProductBinding.inflate(layoutInflater, parent, false)

            return ShoppingProductViewHolder(binding)
        }
    }
}
