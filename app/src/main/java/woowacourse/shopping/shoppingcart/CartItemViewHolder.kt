package woowacourse.shopping.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.common.CountPickerListener
import woowacourse.shopping.databinding.ItemShoppingCartBinding
import woowacourse.shopping.model.CartProductUiModel

class CartItemViewHolder private constructor(
    private val binding: ItemShoppingCartBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun setOnClicked(
        onRemoveClicked: (product: CartProductUiModel) -> Unit,
        onSelectingChanged: (product: CartProductUiModel, isSelected: Boolean) -> Unit,
    ) {
        with(binding) {
            imageRemoveProduct.setOnClickListener {
                onRemoveClicked(
                    product ?: return@setOnClickListener
                )
            }
            checkBoxSelecting.setOnCheckedChangeListener { _, isChecked ->
                onSelectingChanged(
                    product ?: return@setOnCheckedChangeListener,
                    isChecked
                )
            }
        }
    }

    fun bind(
        product: CartProductUiModel,
        getCountPickerListener: (product: CartProductUiModel) -> CountPickerListener,
    ) {
        with(binding) {
            this.product = product
            countPicker.setListener(
                listener = getCountPickerListener(binding.product ?: return)
            )
            countPicker.setTextCount(product.count)
        }
    }

    companion object {
        fun from(parent: ViewGroup): CartItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemShoppingCartBinding.inflate(layoutInflater, parent, false)

            return CartItemViewHolder(binding)
        }
    }
}
