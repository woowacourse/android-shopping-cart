package woowacourse.shopping.feature.list.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.feature.list.item.ProductView.CartProductItem

class CartProductViewHolder(
    val parent: ViewGroup,
    onDeleteItem: (Int) -> Unit,
    onCheckItem: (Int) -> Unit,
    onPlusItem: (Int) -> Unit,
    onMinusItem: (Int) -> Unit,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_cart_product, parent, false),
) {
    private val binding = ItemCartProductBinding.bind(itemView)

    init {
        binding.cartProductDeleteImageView.setOnClickListener { onDeleteItem(adapterPosition) }
        binding.cartProductPlusTv.setOnClickListener { onPlusItem(adapterPosition) }
        binding.cartProductMinusTv.setOnClickListener { onMinusItem(adapterPosition) }
        binding.cartProductCheckBox.setOnClickListener { onCheckItem(adapterPosition) }
    }

    fun bind(cartProductItem: CartProductItem, isSelected: Boolean) {
        binding.cartProduct = cartProductItem
        binding.cartProductCheckBox.isChecked = isSelected
    }
}
