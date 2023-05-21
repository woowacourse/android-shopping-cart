package woowacourse.shopping.shoppingcart.adapter.viewholder

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
        onRemoveClicked: (id: Int) -> Unit,
        onSelectingChanged: (id: Int, isSelected: Boolean) -> Unit,
    ) {
        with(binding) {
            imageRemoveProduct.setOnClickListener {
                onRemoveClicked(
                    product?.id ?: return@setOnClickListener
                )
            }
            checkBoxSelecting.setOnCheckedChangeListener { _, isChecked ->
                onSelectingChanged(
                    product?.id ?: return@setOnCheckedChangeListener,
                    isChecked
                )
            }
        }
    }

    fun bind(
        product: CartProductUiModel,
        getCountPickerListener: (id: Int) -> CountPickerListener,
    ) {
        with(binding) {
            this.product = product
            countPicker.setListener(
                countPickerListener = getCountPickerListener(binding.product?.id ?: return)
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
