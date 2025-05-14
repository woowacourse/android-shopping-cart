package woowacourse.shopping.presentation.view.catalog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.databinding.FragmentCatalogBinding
import woowacourse.shopping.presentation.base.BaseFragment
import woowacourse.shopping.presentation.custom.GridSpacingItemDecoration
import woowacourse.shopping.presentation.model.ProductUiModel
import woowacourse.shopping.presentation.view.cart.CartFragment
import woowacourse.shopping.presentation.view.catalog.adapter.CatalogAdapter
import woowacourse.shopping.presentation.view.detail.DetailFragment

class CatalogFragment :
    BaseFragment<FragmentCatalogBinding>(R.layout.fragment_catalog),
    CatalogAdapter.CatalogEventListener {
    private val catalogAdapter: CatalogAdapter by lazy { CatalogAdapter(eventListener = this) }
    private val viewModel: CatalogViewModel by viewModels { CatalogViewModel.Factory }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        initListener()
        setCatalogAdapter()
    }

    private fun setCatalogAdapter() {
        binding.recyclerViewProducts.layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
        binding.recyclerViewProducts.addItemDecoration(
            GridSpacingItemDecoration(
                SPAN_COUNT,
                ITEM_SPACING,
            ),
        )
        binding.recyclerViewProducts.adapter = catalogAdapter
    }

    private fun initObserver() {
        viewModel.products.observe(viewLifecycleOwner) { products ->
            catalogAdapter.updateProducts(products)
        }
    }

    private fun initListener() {
        binding.btnNavigateCart.setOnClickListener {
            navigateToScreen(CartFragment::class.java)
        }
    }

    override fun onProductClicked(product: ProductUiModel) {
        navigateToScreen(DetailFragment::class.java, DetailFragment.newBundle(product))
    }

    private fun navigateToScreen(
        fragment: Class<out Fragment>,
        bundle: Bundle? = null,
    ) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.shopping_fragment_container, fragment, bundle)
            addToBackStack(null)
        }
    }

    companion object {
        private const val SPAN_COUNT = 2
        private const val ITEM_SPACING = 12f
    }
}
