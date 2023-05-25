package woowacourse.shopping.ui.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.ui.cart.uistate.CartItemUIState
import woowacourse.shopping.utils.PRICE_FORMAT

class CartListViewHolder private constructor(
    private val binding: ItemCartBinding,
    private val onClickCloseButton: (Long) -> Unit,
    private val onClickCheckBox: (Long, Boolean) -> Unit,
    private val onClickPlus: (Long) -> Unit,
    private val onClickMinus: (Long) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.cbCartItemSelected.setOnCheckedChangeListener { _, isChecked ->
            onClickCheckBox(binding.cart?.id ?: return@setOnCheckedChangeListener, isChecked)
        }
        binding.btnCartClose.setOnClickListener {
            onClickCloseButton(binding.cart?.id ?: return@setOnClickListener)
        }
        binding.counter.tvPlus.setOnClickListener { onClickPlus(binding.cart?.id ?: return@setOnClickListener) }
        binding.counter.tvMinus.setOnClickListener { onClickMinus(binding.cart?.id ?: return@setOnClickListener) }
    }

    fun bind(cartItem: CartItemUIState) {
        binding.cart = cartItem
        binding.tvCartName.text = cartItem.name
        binding.tvCartPrice.text = itemView.context.getString(R.string.product_price).format(
            PRICE_FORMAT.format(cartItem.price),
        )
        Glide.with(itemView)
            .load(cartItem.imageUrl)
            .into(binding.ivCart)
        binding.cbCartItemSelected.isChecked = cartItem.isSelected

        binding.counter.count = cartItem.count
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onClickCloseButton: (Long) -> Unit,
            onClickCheckBox: (Long, Boolean) -> Unit,
            onClickPlus: (Long) -> Unit,
            onClickMinus: (Long) -> Unit
        ): CartListViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_cart, parent, false)
            val binding = ItemCartBinding.bind(view)
            return CartListViewHolder(
                binding,
                onClickCloseButton,
                onClickCheckBox,
                onClickPlus,
                onClickMinus
            )
        }
    }
}