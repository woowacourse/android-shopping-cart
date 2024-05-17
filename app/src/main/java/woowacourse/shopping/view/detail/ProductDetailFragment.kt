package woowacourse.shopping.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.databinding.FragmentProductDetailBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.utils.NoSuchDataException
import woowacourse.shopping.view.ViewModelFactory

class ProductDetailFragment : Fragment(), OnClickDetail {
    private var _binding: FragmentProductDetailBinding? = null
    val binding: FragmentProductDetailBinding get() = _binding!!
    private val productDetailViewModel: ProductDetailViewModel by lazy {
        val viewModelFactory = ViewModelFactory { ProductDetailViewModel(ProductRepositoryImpl(context = requireContext())) }
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
        loadProduct()
        observeData()
    }

    private fun observeData()  {
        productDetailViewModel.product.observe(viewLifecycleOwner) { product ->
            initView(product)
        }
    }

    private fun receiveId(): Long {
        return arguments?.getLong(PRODUCT_ID) ?: throw NoSuchDataException()
    }

    private fun loadProduct(): Product? {
        runCatching {
            productDetailViewModel.loadProductItem(receiveId())
        }.onFailure {
            showLoadErrorMessage()
            parentFragmentManager.popBackStack()
        }
        return null
    }

    private fun initView(product: Product) {
        binding.product = product
        binding.onClickDetail = this
        Glide.with(requireActivity())
            .load(product.imageUrl)
            .override(Target.SIZE_ORIGINAL)
            .into(binding.ivDetail)
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
            productDetailViewModel.addShoppingCartItem(product)
        }.onSuccess {
            showAddCartSuccessMessage()
        }.onFailure {
            showAddCartErrorMessage()
        }
    }

    private fun showLoadErrorMessage() = Toast.makeText(this.context, ERROR_DATA_LOAD_MESSAGE, Toast.LENGTH_SHORT).show()

    private fun showAddCartSuccessMessage() = Toast.makeText(this.context, SUCCESS_SAVE_DATA, Toast.LENGTH_SHORT).show()

    private fun showAddCartErrorMessage() = Toast.makeText(this.context, ERROR_SAVE_DATA, Toast.LENGTH_SHORT).show()

    companion object {
        fun createBundle(id: Long): Bundle {
            return Bundle().apply { putLong(PRODUCT_ID, id) }
        }

        private const val ERROR_SAVE_DATA = "장바구니에 담기지 않았습니다.."
        private const val SUCCESS_SAVE_DATA = "장바구니에 담겼습니다!"
        private const val PRODUCT_ID = "productId"
        private const val ERROR_DATA_LOAD_MESSAGE = "데이터가 없습니다!"
    }
}
