package woowacourse.shopping.ui.cart.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import woowacourse.shopping.databinding.CartItemBinding
import woowacourse.shopping.model.CartProductUIModel
import woowacourse.shopping.ui.cart.contract.CartContract
import woowacourse.shopping.ui.cart.contract.presenter.CartPresenter

class CartViewHolder private constructor(
    val binding: CartItemBinding,
    private val lifecycleOwner: LifecycleOwner,
    private val presenter: CartContract.Presenter,
    private val onCartClickListener: OnCartClickListener,
) : ItemViewHolder(binding.root) {
    fun bind(cart: CartProductUIModel) {
        binding.product = cart
        binding.listener = onCartClickListener
        binding.lifecycleOwner = lifecycleOwner
        binding.presenter = presenter as CartPresenter?
        /*binding.orderCountLayout.minusBtn.setOnClickListener {
            cart.count.set(cart.count.get() - 1)
            onCountChanged(cart.product.id, cart.count.get())
        }
        binding.orderCountLayout.plusBtn.setOnClickListener {
            cart.count.set(cart.count.get() + 1)
            onCountChanged(cart.product.id, cart.count.get())
        }
        binding.checkBox.setOnClickListener {
            cart.isChecked.set(cart.isChecked.get().not())
            onCheckChanged(cart.product.id, cart.isChecked.get())
        }*/
    }

    companion object {
        fun from(
            parent: ViewGroup,
            lifecycleOwner: LifecycleOwner,
            presenter: CartContract.Presenter,
            onCartClickListener: OnCartClickListener,
        ): CartViewHolder {
            val binding = CartItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return CartViewHolder(binding, lifecycleOwner, presenter, onCartClickListener)
        }
    }
}
