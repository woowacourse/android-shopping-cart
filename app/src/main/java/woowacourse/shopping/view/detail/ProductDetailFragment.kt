package woowacourse.shopping.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import woowacourse.shopping.R
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.data.repository.RecentViewedItemRepositoryImpl
import woowacourse.shopping.databinding.FragmentProductDetailBinding
import woowacourse.shopping.utils.NoSuchDataException
import woowacourse.shopping.view.MainViewModel

class ProductDetailFragment : Fragment() {
    private var _binding: FragmentProductDetailBinding? = null
    val binding: FragmentProductDetailBinding get() = _binding!!

    private val sharedViewModel: MainViewModel by activityViewModels()
    private val productDetailViewModel: ProductDetailViewModel by lazy {
        val viewModelFactory =
            DetailViewModelFactory(
                ProductRepositoryImpl(requireContext()),
                CartRepositoryImpl(requireContext()),
                RecentViewedItemRepositoryImpl(requireContext()),
                receiveProductId(),
            )
        viewModelFactory.create(ProductDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupDataBinding()
        observeData()
    }

    private fun setupDataBinding() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = productDetailViewModel
        binding.detailActionHandler = productDetailViewModel
        binding.countActionHandler = productDetailViewModel
    }

    private fun observeData() {
        productDetailViewModel.cartItemSavedState.observe(viewLifecycleOwner) {
            when (it) {
                is ProductDetailState.Success -> {
                    showAddCartSuccessMessage()
                    sharedViewModel.setUpdateProductEvent(it.updatedProductId, it.updatedValue)
                }
                is ProductDetailState.Fail -> showAddCartErrorMessage()
            }
        }

        productDetailViewModel.navigateToBack.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { shouldUpdate ->
                if (shouldUpdate) {
                    productDetailViewModel.product.value?.let { product ->
                        sharedViewModel.setUpdatedRecentViewedProduct(product)
                    }
                }
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun receiveProductId(): Long {
        return arguments?.getLong(PRODUCT_ID) ?: throw NoSuchDataException()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showAddCartSuccessMessage() = showToastMessage(R.string.success_save_cart_item_message)

    private fun showAddCartErrorMessage() = showToastMessage(R.string.error_save_cart_item_message)

    private fun showToastMessage(message: Int) = Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()

    companion object {
        fun createBundle(
            productId: Long,
            recentProductId: Long = -1,
        ): Bundle {
            return Bundle().apply {
                putLong(PRODUCT_ID, productId)
                putLong(RECENT_VIEWED_PRODUCT_ID, recentProductId)
            }
        }

        private const val PRODUCT_ID = "productId"
        private const val RECENT_VIEWED_PRODUCT_ID = "recentViewedProductId"
    }
}
