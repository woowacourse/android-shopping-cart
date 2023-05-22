package woowacourse.shopping.presentation.cart.viewholder

import androidx.core.view.doOnAttach
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.presentation.cart.CartContract
import woowacourse.shopping.presentation.common.CounterContract

class CartItemViewHolder(
    private val binding: ItemCartBinding,
    private val presenter: CartContract.Presenter,
) : RecyclerView.ViewHolder(binding.root) {
    private val counterPresenter: CounterContract.Presenter =
        binding.counterCartProduct.presenter

    init {
        itemView.doOnAttach {
            setUpBinding()
            setUpView()
        }
    }

    private fun setUpBinding() {
        binding.lifecycleOwner = itemView.findViewTreeLifecycleOwner()
        binding.presenter = presenter
        binding.position = adapterPosition
    }

    private fun setUpView() {
        counterPresenter.updateCount(presenter.pageProducts.value.items[adapterPosition].count)
        setDeleteButtonClick()
        setPlusButtonClick()
        setMinusButtonClick()
        setCheckBoxCheckedChange()
    }

    private fun setDeleteButtonClick() {
        binding.imageCartDelete.setOnClickListener {
            presenter.deleteProductItem(adapterPosition)
            presenter.updateCurrentPageCartView()
            presenter.checkPlusPageAble()
        }
    }

    private fun setPlusButtonClick() {
        binding.counterCartProduct.plusButton.setOnClickListener {
            binding.counterCartProduct.presenter.plusCount()
            presenter.updateProductCount(
                adapterPosition,
                counterPresenter.counter.value.value,
            )
        }
    }

    private fun setMinusButtonClick() {
        binding.counterCartProduct.minusButton.setOnClickListener {
            binding.counterCartProduct.presenter.minusCount()
            presenter.updateProductCount(
                adapterPosition,
                counterPresenter.counter.value.value,
            )
        }
    }

    private fun setCheckBoxCheckedChange() {
        binding.checkboxProductCart.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                presenter.addProductInOrder(adapterPosition)
            } else {
                presenter.deleteProductInOrder(adapterPosition)
            }
        }
    }
}
