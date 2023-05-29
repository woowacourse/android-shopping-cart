package woowacourse.shopping.cart.viewHolder

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.CartNavigationItemBinding
import woowacourse.shopping.model.CartNavigationUIModel

class CartNavigationViewHolder private constructor(
    private val binding: CartNavigationItemBinding,
    onPageUp: () -> Unit,
    onPageDown: () -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.pageUp.setOnClickListener { onPageUp() }
        binding.pageDown.setOnClickListener { onPageDown() }
    }

    fun bind(cart: CartNavigationUIModel) {
        binding.cartNavigation.visibility = when {
            cart.pageUp -> VISIBLE
            cart.pageDown -> VISIBLE
            else -> GONE
        }
        binding.cart = cart
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onPageUp: () -> Unit,
            onPageDown: () -> Unit,
        ): CartNavigationViewHolder {
            val binding = CartNavigationItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return CartNavigationViewHolder(binding, onPageUp, onPageDown)
        }
    }
}
