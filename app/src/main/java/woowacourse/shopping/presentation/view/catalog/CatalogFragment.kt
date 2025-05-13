package woowacourse.shopping.presentation.view.catalog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.databinding.FragmentCatalogBinding
import woowacourse.shopping.presentation.base.BaseFragment
import woowacourse.shopping.presentation.custom.GridSpacingItemDecoration
import woowacourse.shopping.presentation.view.catalog.adapter.CatalogAdapter

class CatalogFragment : BaseFragment<FragmentCatalogBinding>(R.layout.fragment_catalog) {
    private val catalogAdapter: CatalogAdapter by lazy { CatalogAdapter() }
    private val viewModel: CatalogViewModel by viewModels { CatalogViewModel.Factory }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        setCatalogAdapter()
    }

    private fun setCatalogAdapter() {
        binding.recyclerViewProducts.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerViewProducts.addItemDecoration(GridSpacingItemDecoration(2, 12f))
        binding.recyclerViewProducts.adapter = catalogAdapter
    }

    private fun initObserver() {
        viewModel.products.observe(viewLifecycleOwner) { products ->
            catalogAdapter.updateProducts(products)
        }
    }
}
