package woowacourse.shopping.view.products

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.shopping.R
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.data.repository.ShoppingCartRepositoryImpl
import woowacourse.shopping.databinding.FragmentProductListBinding
import woowacourse.shopping.domain.model.CartItemCounter
import woowacourse.shopping.utils.ShoppingUtils.makeToast
import woowacourse.shopping.view.FragmentChangeListener
import woowacourse.shopping.view.ViewModelFactory
import woowacourse.shopping.view.cart.ShoppingCartFragment
import woowacourse.shopping.view.cartcounter.ChangeCartItemResultState
import woowacourse.shopping.view.cartcounter.OnClickCartItemCounter
import woowacourse.shopping.view.detail.ProductDetailFragment
import woowacourse.shopping.view.products.adapter.ProductAdapter

class ProductsListFragment : Fragment(), OnClickProducts, OnClickCartItemCounter {
    private var fragmentChangeListener: FragmentChangeListener? = null
    private var _binding: FragmentProductListBinding? = null
    val binding: FragmentProductListBinding get() = _binding!!
    private val productListViewModel: ProductListViewModel by lazy {
        val viewModelFactory = ViewModelFactory {
            ProductListViewModel(
                productRepository = ProductRepositoryImpl(),
                shoppingCartRepository = ShoppingCartRepositoryImpl(requireContext()),
            )
        }
        viewModelFactory.create(ProductListViewModel::class.java)
    }
    private lateinit var adapter: ProductAdapter

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
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
    }

    private fun initView() {
        loadPagingData()
        binding.onClickProduct = this
        binding.lifecycleOwner = viewLifecycleOwner
        adapter =
            ProductAdapter(
                onClickProducts = this,
                onClickCartItemCounter = this,
            ) { isLoadLastItem ->
                binding.isVisible = isLoadLastItem
            }
        binding.rvProducts.adapter = adapter
    }

    private fun observeData() {
        productListViewModel.products.observe(viewLifecycleOwner) { products ->
            adapter.updateProducts(addedProducts = products)
        }
        productListViewModel.errorState.observe(viewLifecycleOwner) { errorState ->
            when (errorState) {
                ProductListState.LoadProductList.Fail ->
                    requireContext().makeToast(
                        getString(R.string.max_paging_data),
                    )
                ProductListState.ErrorState.NotKnownError ->
                    requireContext().makeToast(
                        getString(R.string.error_default),
                    )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        fragmentChangeListener = null
    }

    override fun clickProductItem(productId: Long) {
        val productFragment =
            ProductDetailFragment().apply {
                arguments = ProductDetailFragment.createBundle(productId)
            }
        fragmentChangeListener?.changeFragment(productFragment)
    }

    override fun clickShoppingCart() {
        val shoppingCartFragment = ShoppingCartFragment()
        fragmentChangeListener?.changeFragment(shoppingCartFragment)
    }

    override fun clickLoadPagingData() {
        loadPagingData()
    }

    private fun loadPagingData() {
        productListViewModel.loadPagingProduct()
    }

    override fun clickIncrease(
        productId: Long,
        itemPosition: Int,
        cartItemCounter: CartItemCounter
    ) {
        cartItemCounter.selectItem()
        val resultState =  cartItemCounter.increase()
        when(resultState){
            ChangeCartItemResultState.Success -> {
                adapter.updateProduct(itemPosition)
            }
            ChangeCartItemResultState.Fail -> requireContext().makeToast(
                getString(R.string.max_cart_item),
            )
        }
    }

    override fun clickDecrease(
        productId: Long,
        itemPosition: Int,
        cartItemCounter: CartItemCounter
    ) {
        val resultState = cartItemCounter.decrease()
        when(resultState){
            ChangeCartItemResultState.Success -> {
                adapter.updateProduct(itemPosition)
            }
            ChangeCartItemResultState.Fail -> {

                requireContext().makeToast(
                    getString(R.string.delete_cart_item),
                )
            }
        }
    }
}
