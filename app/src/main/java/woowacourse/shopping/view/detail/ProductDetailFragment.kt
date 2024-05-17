package woowacourse.shopping.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import woowacourse.shopping.R
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.data.repository.ShoppingCartRepositoryImpl
import woowacourse.shopping.databinding.FragmentProductDetailBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.utils.NoSuchDataException
import woowacourse.shopping.view.ViewModelFactory

class ProductDetailFragment : Fragment(), OnClickDetail {
    private var _binding: FragmentProductDetailBinding? = null
    val binding: FragmentProductDetailBinding get() = _binding!!
    private val productDetailViewModel: ProductDetailViewModel by lazy {
        val viewModelFactory =
            ViewModelFactory {
                ProductDetailViewModel(
                    productRepository = ProductRepositoryImpl(),
                    shoppingCartRepository = ShoppingCartRepositoryImpl(requireContext()),
                )
            }
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

    private fun observeData() {
        productDetailViewModel.product.observe(viewLifecycleOwner) { product ->
            initView(product)
        }
        productDetailViewModel.productDetailState.observe(viewLifecycleOwner) { productDetailState ->
            when (productDetailState) {
                ProductDetailState.Init -> {}
                ProductDetailState.AddShoppingCart.Success -> showMessage(
                    requireContext().getString(
                        R.string.success_save_data
                    )
                )

                ProductDetailState.AddShoppingCart.Fail -> showMessage(requireContext().getString(R.string.error_save_data))
                ProductDetailState.LoadProductItem.Success -> {}
                ProductDetailState.LoadProductItem.Fail -> {
                    showMessage(requireContext().getString(R.string.error_data_load))
                    parentFragmentManager.popBackStack()
                }
            }
        }
    }

    private fun receiveId(): Long {
        return arguments?.getLong(PRODUCT_ID) ?: throw NoSuchDataException()
    }

    private fun loadProduct() {
        productDetailViewModel.loadProductItem(receiveId())
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
        productDetailViewModel.addShoppingCartItem(product)
    }


    private fun showMessage(message: String) =
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()

    companion object {
        fun createBundle(id: Long): Bundle {
            return Bundle().apply { putLong(PRODUCT_ID, id) }
        }

        private const val PRODUCT_ID = "productId"
    }
}
