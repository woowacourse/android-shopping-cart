package woowacourse.shopping.ui.productDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.databinding.FragmentProductDetailBinding
import woowacourse.shopping.ui.FragmentNavigator
import woowacourse.shopping.ui.UniversalViewModelFactory
import woowacourse.shopping.ui.productDetail.event.ProductDetailError
import woowacourse.shopping.ui.productDetail.event.ProductDetailEvent

class ProductDetailFragment : Fragment() {
    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("FragmentCartListBinding is not initialized")

    private lateinit var factory: UniversalViewModelFactory
    private lateinit var viewModel: ProductDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater)

        initViewModel()

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.productDetailListener = viewModel

        return binding.root
    }

    private fun initViewModel() {
        arguments?.let {
            factory = DefaultProductDetailViewModel.factory(productId = it.getLong(PRODUCT_ID))
        }
        viewModel = ViewModelProvider(this, factory)[DefaultProductDetailViewModel::class.java]
        viewModel.loadAll()
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        observeEvent()
        observeError()
    }

    private fun observeEvent() {
        viewModel.event.observe(viewLifecycleOwner) { event ->
            when (event) {
                is ProductDetailEvent.NavigateToProductDetail -> navigateToProductDetail(event.productId)
                is ProductDetailEvent.NavigateToProductList -> navigateToProductList()
                is ProductDetailEvent.AddProductToCart -> showToast(R.string.message_save_product_in_cart)
            }
        }
    }

    private fun observeError() {
        viewModel.error.observe(viewLifecycleOwner) { error ->
            when (error) {
                is ProductDetailError.LoadProduct -> showToast(R.string.error_message_load_product)
                is ProductDetailError.LoadLatestProduct -> showToast(R.string.error_message_load_product)
                is ProductDetailError.SaveProductInHistory -> showToast(R.string.error_message_save_product_in_history)
                is ProductDetailError.AddProductToCart -> showToast(R.string.error_message_add_product_in_cart)
            }
        }
    }

    private fun showToast(@StringRes stringId: Int) {
        Toast.makeText(requireContext(), stringId, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToProductDetail(id: Long) =
        (requireActivity() as? FragmentNavigator)?.navigateToProductDetail(id)

    private fun navigateToProductList() = (requireActivity() as? FragmentNavigator)?.navigateToProductList()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val PRODUCT_ID = "productId"
        const val TAG = "ProductDetailFragment"

        fun newInstance(productId: Long): ProductDetailFragment {
            val fragment = ProductDetailFragment()
            val bundle = Bundle().apply { putLong(PRODUCT_ID, productId) }
            fragment.arguments = bundle
            return fragment
        }
    }
}
