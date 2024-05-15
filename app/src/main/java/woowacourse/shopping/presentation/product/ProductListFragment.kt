package woowacourse.shopping.presentation.product

import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.databinding.FragmentProductListBinding
import woowacourse.shopping.presentation.base.BindingFragment
import woowacourse.shopping.presentation.util.dp

class ProductListFragment :
    BindingFragment<FragmentProductListBinding>(R.layout.fragment_product_list) {
    private val viewModel: ProductListViewModel by lazy {
        ViewModelProvider(this)[ProductListViewModel::class.java]
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            val productAdapter = ProductAdapter(onClickItem = { navigateToDetailView(it.toLong()) })
            rvProductList.adapter = productAdapter
            rvProductList.addItemDecoration(ProductItemDecoration(12.dp))
            // TODO 삭제 ><
            productAdapter.updateProducts(
                listOf(
                    ProductUi(
                        "꼬우상2",
                        1000,
                        "ddddd",
                    ),
                    ProductUi(
                        "꼬우상",
                        1000,
                        "ddddd",
                    ),
                ),
            )
        }
    }

    // TODO ViewModel 로 로작 이동
    private fun navigateToDetailView(id: Long) {
        parentFragmentManager.commit {
            replace<ProductDetailFragment>(
                R.id.fragment_container_shopping,
                ProductDetailFragment.TAG,
            )
            addToBackStack(TAG)
        }
    }

    companion object {
        val TAG: String? = ProductListFragment::class.java.canonicalName
    }
}
