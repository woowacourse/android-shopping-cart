package woowacourse.shopping.presentation.ui.shoppingCart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemShoppingCartProductBinding
import woowacourse.shopping.domain.model.Operator
import woowacourse.shopping.domain.model.ProductInCart

class ShoppingCartViewHolder(
    listener: ShoppingCartClickListener,
    private val binding: ItemShoppingCartProductBinding,
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            listener.clickItem(bindingAdapterPosition)
        }
        binding.buttonCartProductDelete.setOnClickListener {
            listener.clickDelete(bindingAdapterPosition)
        }
        binding.customShoppingCartCounter.setIncreaseClickListener {
            listener.clickChangeQuality(bindingAdapterPosition, Operator.INCREASE)
        }
        binding.customShoppingCartCounter.setDecreaseClickListener {
            listener.clickChangeQuality(bindingAdapterPosition, Operator.DECREASE)
        }
        binding.checkCartProduct.setOnClickListener {
            listener.checkItem(bindingAdapterPosition, (it as CheckBox).isChecked)
        }
        binding.customShoppingCartCounter.setMinValue(1)
    }

    fun bind(product: ProductInCart) {
        binding.product = product
        binding.customShoppingCartCounter.setQuantityText(product.quantity)
    }

    companion object {
        fun getView(parent: ViewGroup): ItemShoppingCartProductBinding {
            val layoutInflater = LayoutInflater.from(parent.context)
            return ItemShoppingCartProductBinding.inflate(layoutInflater, parent, false)
        }
    }
}
