package woowacourse.shopping.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import woowacourse.shopping.R
import woowacourse.shopping.databinding.FragmentProductDetailBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.utils.NoSuchDataException
import woowacourse.shopping.view.MainActivity
import woowacourse.shopping.view.MainViewModel

class ProductDetailFragment : Fragment(), OnClickDetail {
    private var _binding: FragmentProductDetailBinding? = null
    val binding: FragmentProductDetailBinding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel

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
        mainViewModel = (requireActivity() as MainActivity).viewModel
        val product = loadProduct()
        if (product != null) {
            initView(product)
        }
    }

    private fun receiveId(): Long {
        return arguments?.getLong(PRODUCT_ID) ?: throw NoSuchDataException()
    }

    private fun loadProduct(): Product? {
        runCatching {
            mainViewModel.loadProductItem(receiveId())
        }.onSuccess {
            return it
        }.onFailure {
            showLoadErrorMessage()
            parentFragmentManager.popBackStack()
        }
        return null
    }

    private fun initView(product: Product) {
        binding.product = product
        binding.onClickDetail = this
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun clickClose() {
        parentFragmentManager.popBackStack()
    }

    override fun clickAddCart(product: Product) {
        runCatching {
            mainViewModel.addShoppingCartItem(product)
        }.onSuccess {
            showAddCartSuccessMessage()
        }.onFailure {
            showAddCartErrorMessage()
        }
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
