package woowacourse.shopping.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import woowacourse.shopping.R
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.databinding.FragmentProductDetailBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.utils.NoSuchDataException

class ProductDetailFragment : Fragment(), OnClickDetail {
    private var _binding: FragmentProductDetailBinding? = null
    val binding: FragmentProductDetailBinding get() = _binding!!

    private val productDetailViewModel: ProductDetailViewModel by lazy {
        val viewModelFactory = DetailViewModelFactory(ProductRepositoryImpl(context = requireContext()), receiveProductId())
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
        binding.viewModel = productDetailViewModel
        binding.onClickDetail = this
    }

    private fun observeData() {
        productDetailViewModel.cartItemSavedState.observe(viewLifecycleOwner) {
            when (it) {
                is ProductDetailState.Success -> showAddCartSuccessMessage()
                is ProductDetailState.Fail -> showAddCartErrorMessage()
                is ProductDetailState.Idle -> {}
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

    override fun clickClose() {
        parentFragmentManager.popBackStack()
    }

    override fun clickAddCart(product: Product) {
        productDetailViewModel.addShoppingCartItem(product)
    }

    private fun showLoadErrorMessage() = showToastMessage(R.string.error_load_data_message)

    private fun showAddCartSuccessMessage() = showToastMessage(R.string.success_save_cart_item_message)

    private fun showAddCartErrorMessage() = showToastMessage(R.string.error_save_cart_item_message)

    private fun showToastMessage(message: Int) = Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()

    companion object {
        fun createBundle(id: Long): Bundle {
            return Bundle().apply { putLong(PRODUCT_ID, id) }
        }

        private const val PRODUCT_ID = "productId"
    }
}
