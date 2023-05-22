package woowacourse.shopping.ui.shopping.viewHolder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import woowacourse.shopping.databinding.ProductItemBinding
import woowacourse.shopping.ui.shopping.ProductItem
import woowacourse.shopping.ui.shopping.ProductsItemType
import woowacourse.shopping.ui.shopping.contract.ShoppingContract
import woowacourse.shopping.ui.shopping.contract.presenter.ShoppingPresenter

class ProductsViewHolder private constructor(
    private val binding: ProductItemBinding,
    private val presenter: ShoppingContract.Presenter,
    private val lifeCycleOwner: LifecycleOwner,
    private val onClickListener: ProductsOnClickListener,
) :
    ItemViewHolder(binding.root) {

    fun bind(productItemType: ProductsItemType) {
        val productItem = productItemType as? ProductItem ?: return
        binding.product = productItem.product
        binding.lifecycleOwner = lifeCycleOwner
        binding.presenter = presenter as ShoppingPresenter?
        binding.listener = onClickListener

        binding.addCartBtn.setOnClickListener {
            onClickListener.onAddCart(productItem.product.id, 1)
            binding.presenter = presenter
            binding.addCartBtn.visibility = View.GONE
        }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            presenter: ShoppingContract.Presenter,
            lifeCycleOwner: LifecycleOwner,
            onClickListener: ProductsOnClickListener,
        ): ProductsViewHolder {
            val binding = ProductItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return ProductsViewHolder(
                binding,
                presenter,
                lifeCycleOwner,
                onClickListener,
            )
        }
    }
}
