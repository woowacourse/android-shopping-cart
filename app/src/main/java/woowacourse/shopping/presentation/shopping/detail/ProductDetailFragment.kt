package woowacourse.shopping.presentation.shopping.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.data.DefaultShoppingRepository
import woowacourse.shopping.databinding.FragmentProductDetailBinding
import woowacourse.shopping.presentation.base.BindingFragment
import woowacourse.shopping.presentation.shopping.cart.ShoppingCartFragment

class ProductDetailFragment :
    BindingFragment<FragmentProductDetailBinding>(R.layout.fragment_product_detail) {
    private val viewModel: ProductDetailViewModel by lazy {
        val id = arguments?.getLong(PRODUCT_ID, -1) ?: -1
        ViewModelProvider(
            this,
            ProductDetailViewModel.factory(DefaultShoppingRepository())
        )[ProductDetailViewModel::class.java]
            .apply { loadProduct(id) }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding?.also {
            it.lifecycleOwner = viewLifecycleOwner
            it.vm = viewModel
        }
        binding?.btnProductCart?.setOnClickListener {
            navigateToShoppingCart()
        }
    }

    private fun navigateToShoppingCart() {
        parentFragmentManager.commit {
            replace<ShoppingCartFragment>(
                R.id.fragment_container_shopping,
                ShoppingCartFragment.TAG
            )
            addToBackStack(TAG)
        }
    }

    companion object {
        val TAG: String? = ProductDetailFragment::class.java.canonicalName
        const val PRODUCT_ID = "PRODUCT_ID"

        fun args(id: Long): Bundle =
            Bundle().apply {
                putLong(PRODUCT_ID, id)
            }
    }
}
