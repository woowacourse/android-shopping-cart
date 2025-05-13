package woowacourse.shopping.view.main.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import woowacourse.shopping.R
import woowacourse.shopping.databinding.FragmentProductsBinding
import woowacourse.shopping.view.main.MainViewModel
import woowacourse.shopping.view.main.MainViewModelFactory
import woowacourse.shopping.view.main.adapter.ProductAdapter

class ProductsFragment : Fragment(R.layout.fragment_products) {
    private val viewModel: MainViewModel by activityViewModels { MainViewModelFactory() }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val productAdapter = ProductAdapter()
        val binding = FragmentProductsBinding.bind(view)
        binding.recyclerViewProduct.adapter = productAdapter
        viewModel.products.observe(viewLifecycleOwner) { value ->
            productAdapter.submitList(value)
        }
    }
}
