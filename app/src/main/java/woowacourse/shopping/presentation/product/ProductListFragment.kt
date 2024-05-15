package woowacourse.shopping.presentation.product

import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.data.DefaultShoppingRepository
import woowacourse.shopping.databinding.FragmentProductListBinding
import woowacourse.shopping.presentation.base.BindingFragment
import woowacourse.shopping.presentation.util.dp

class ProductListFragment :
    BindingFragment<FragmentProductListBinding>(R.layout.fragment_product_list) {
    private val viewModel: ProductListViewModel by lazy {
        ViewModelProvider(
            this,
            ProductListViewModel.factory(DefaultShoppingRepository()),
        )[ProductListViewModel::class.java]
    }
    private lateinit var productAdapter: ProductAdapter

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }

        initViews()
        initObservers()
    }

    private fun initViews() {
        binding?.apply {
            productAdapter =
                ProductAdapter(onClickItem = {
                    navigateToDetailView(it)
                })
            rvProductList.adapter = productAdapter
            rvProductList.addItemDecoration(ProductItemDecoration(12.dp))
        }
    }

    private fun initObservers() {
        viewModel.products.observe(viewLifecycleOwner) {
            productAdapter.updateProducts(it)
        }
    }

    private fun navigateToDetailView(id: Long) {
        parentFragmentManager.commit {
            replace<ProductDetailFragment>(
                R.id.fragment_container_shopping,
                ProductDetailFragment.TAG,
                ProductDetailFragment.args(id),
            )
            addToBackStack(TAG)
        }
    }

    companion object {
        val TAG: String? = ProductListFragment::class.java.canonicalName
    }
}
