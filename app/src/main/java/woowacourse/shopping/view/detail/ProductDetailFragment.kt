package woowacourse.shopping.view.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import woowacourse.shopping.R
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.data.repository.ShoppingCartRepositoryImpl
import woowacourse.shopping.databinding.FragmentProductDetailBinding
import woowacourse.shopping.domain.model.CartItemCounter
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.utils.NoSuchDataException
import woowacourse.shopping.utils.ShoppingUtils.makeToast
import woowacourse.shopping.view.FragmentChangeListener
import woowacourse.shopping.view.ViewModelFactory
import woowacourse.shopping.view.cartcounter.OnClickCartItemCounter

class ProductDetailFragment : Fragment(), OnClickDetail, OnClickCartItemCounter {
    private var fragmentChangeListener: FragmentChangeListener? = null
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentChangeListener) {
            fragmentChangeListener = context
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
        productDetailViewModel.productDetailState.observe(viewLifecycleOwner) { productDetailState ->
            if (productDetailState == ProductDetailState.AddShoppingCart.Success) {
                requireContext().makeToast(
                    requireContext().getString(
                        R.string.success_save_data,
                    ),
                )
            }
        }
        productDetailViewModel.errorState.observe(viewLifecycleOwner) { errorState ->
            when (errorState) {
                ProductDetailState.AddShoppingCart.Fail ->
                    requireContext().makeToast(
                        getString(R.string.error_save_data),
                    )

                ProductDetailState.LoadProductItem.Fail -> {
                    requireContext().makeToast(
                        getString(R.string.error_data_load)
                    )
                    parentFragmentManager.popBackStack()
                }

                ProductDetailState.ErrorState.NotKnownError ->
                    requireContext().makeToast(
                        getString(R.string.error_default),
                    )
            }
        }
    }

    private fun receiveId(): Long {
        return arguments?.getLong(PRODUCT_ID) ?: throw NoSuchDataException()
    }

    private fun loadProduct() {
        productDetailViewModel.loadProductItem(receiveId())
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
        fragmentChangeListener = null
    }

    override fun clickClose() {
        fragmentChangeListener?.popFragment()
    }

    override fun clickAddCart(product: Product) {
        productDetailViewModel.addShoppingCartItem(product)
    }

    companion object {
        fun createBundle(id: Long): Bundle {
            return Bundle().apply { putLong(PRODUCT_ID, id) }
        }

        private const val PRODUCT_ID = "productId"
    }

    override fun clickIncrease(
        product: Product,
        itemPosition: Int,
        cartItemCounter: CartItemCounter
    ) {
        productDetailViewModel.increaseItemCounter()
    }

    override fun clickDecrease(
        product: Product,
        itemPosition: Int,
        cartItemCounter: CartItemCounter
    ) {
        productDetailViewModel.decreaseItemCounter()
    }
}
