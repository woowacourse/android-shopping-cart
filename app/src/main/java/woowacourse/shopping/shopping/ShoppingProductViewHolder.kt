package woowacourse.shopping.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import woowacourse.shopping.common.CountPickerListener
import woowacourse.shopping.databinding.ItemShoppingProductBinding
import woowacourse.shopping.model.ProductUiModel

class ShoppingProductViewHolder private constructor(
    binding: ItemShoppingProductBinding,
    private val onProductImageClicked: (product: ProductUiModel) -> Unit,
    private val onAddToCartButtonClicked: (product: ProductUiModel) -> Unit,
    private val getCountPickerListener: (product: ProductUiModel) -> CountPickerListener,
) : ShoppingRecyclerItemViewHolder<ShoppingRecyclerItem.ShoppingProduct, ItemShoppingProductBinding>(binding) {

    private lateinit var getlistener: (product: ProductUiModel) -> CountPickerListener

    init {
        setOnClicked()
    }

    private fun setOnClicked() {
        with(binding) {
            imageProduct.setOnClickListener {
                onProductImageClicked(product ?: return@setOnClickListener)
            }
            imageAddToCart.setOnClickListener {
                it.isVisible = false
                countPicker.isVisible = true
                onAddToCartButtonClicked(product ?: return@setOnClickListener)
            }
            getlistener = getCountPickerListener
        }
    }

    override fun bind(
        itemData: ShoppingRecyclerItem.ShoppingProduct,
    ) {
        binding.product = itemData.value
        binding.countPicker.setListener(
            listener = getlistener(itemData.value)
        )
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onProductImageClicked: (product: ProductUiModel) -> Unit,
            onAddToCartButtonClicked: (product: ProductUiModel) -> Unit,
            getCountPickerListener: (product: ProductUiModel) -> CountPickerListener,
        ): ShoppingProductViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemShoppingProductBinding.inflate(layoutInflater, parent, false)

            return ShoppingProductViewHolder(
                binding = binding,
                onProductImageClicked = onProductImageClicked,
                onAddToCartButtonClicked = onAddToCartButtonClicked,
                getCountPickerListener = getCountPickerListener
            )
        }
    }
}
