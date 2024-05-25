package woowacourse.shopping.view.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.shopping.R
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.data.repository.RecentlyProductRepositoryImpl
import woowacourse.shopping.data.repository.ShoppingCartRepositoryImpl
import woowacourse.shopping.databinding.FragmentProductDetailBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.RecentlyProduct
import woowacourse.shopping.utils.ShoppingUtils.makeToast
import woowacourse.shopping.utils.exception.NoSuchDataException
import woowacourse.shopping.view.MainActivityListener
import woowacourse.shopping.view.ViewModelFactory
import woowacourse.shopping.view.cartcounter.OnClickCartItemCounter

class ProductDetailFragment : Fragment(), OnClickDetail, OnClickCartItemCounter {
    private var mainActivityListener: MainActivityListener? = null
    private var _binding: FragmentProductDetailBinding? = null
    val binding: FragmentProductDetailBinding get() = _binding!!
    private val productDetailViewModel: ProductDetailViewModel by lazy {
        val viewModelFactory =
            ViewModelFactory {
                ProductDetailViewModel(
                    productRepository = ProductRepositoryImpl(),
                    shoppingCartRepository = ShoppingCartRepositoryImpl(requireContext()),
                    recentlyProductRepository = RecentlyProductRepositoryImpl(requireContext()),
                )
            }
        viewModelFactory.create(ProductDetailViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivityListener) {
            mainActivityListener = context
        }
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
        initView()
        loadProduct()
        observeData()
    }

    private fun observeData() {
        productDetailViewModel.productDetailEvent.observe(viewLifecycleOwner) { productDetailState ->
            when (productDetailState) {
                is ProductDetailEvent.AddShoppingCart.Success -> {
                    mainActivityListener?.saveUpdateProduct(
                        productDetailState.productId,
                        productDetailState.count,
                    )
                    requireContext().makeToast(
                        getString(R.string.add_cart_text),
                    )
                }

                ProductDetailEvent.UpdateRecentlyProductItem.Success -> {
                    mainActivityListener?.saveUpdateRecentlyProduct()
                }
            }
        }

        productDetailViewModel.errorEvent.observe(viewLifecycleOwner) { errorState ->
            when (errorState) {
                ProductDetailEvent.AddShoppingCart.Fail ->
                    requireContext().makeToast(
                        getString(R.string.error_save_data),
                    )

                ProductDetailEvent.LoadProductItem.Fail -> {
                    requireContext().makeToast(
                        getString(R.string.error_data_load),
                    )
                    parentFragmentManager.popBackStack()
                }

                ProductDetailEvent.ErrorEvent.NotKnownError ->
                    requireContext().makeToast(
                        getString(R.string.error_default),
                    )

                ProductDetailEvent.UpdateRecentlyProductItem.Fail ->
                    requireContext().makeToast(
                        getString(R.string.error_recently_product_item),
                    )
            }
        }
    }

    private fun receiveId(): Long {
        return arguments?.getLong(PRODUCT_ID) ?: throw NoSuchDataException()
    }

    private fun loadProduct() {
        try {
            productDetailViewModel.loadProductItem(receiveId())
            productDetailViewModel
        } catch (e: NoSuchDataException) {
            requireContext().makeToast(
                getString(R.string.error_data_load),
            )
            clickClose()
        }
    }

    private fun initView() {
        binding.vm = productDetailViewModel
        binding.onClickDetail = this
        binding.onClickCartItemCounter = this
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mainActivityListener = null
    }

    override fun clickClose() {
        mainActivityListener?.popFragment()
    }

    override fun clickAddCart(product: Product) {
        productDetailViewModel.addShoppingCartItem(product)
    }

    override fun clickRecently(recentlyProduct: RecentlyProduct) {
        productDetailViewModel.updateRecentlyProduct(recentlyProduct)
    }

    override fun clickIncrease(product: Product) {
        productDetailViewModel.increaseItemCounter()
    }

    override fun clickDecrease(product: Product) {
        productDetailViewModel.decreaseItemCounter()
    }

    companion object {
        fun createBundle(id: Long): Bundle {
            return Bundle().apply { putLong(PRODUCT_ID, id) }
        }

        private const val PRODUCT_ID = "productId"
    }
}
