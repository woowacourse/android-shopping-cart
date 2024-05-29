package woowacourse.shopping.view.products

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
import woowacourse.shopping.databinding.FragmentProductListBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.RecentlyProduct
import woowacourse.shopping.utils.ShoppingUtils.makeToast
import woowacourse.shopping.view.MainActivityListener
import woowacourse.shopping.view.ViewModelFactory
import woowacourse.shopping.view.cart.ShoppingCartFragment
import woowacourse.shopping.view.cartcounter.OnClickCartItemCounter
import woowacourse.shopping.view.detail.ProductDetailFragment
import woowacourse.shopping.view.products.adapter.ProductAdapter
import woowacourse.shopping.view.products.adapter.RecentlyAdapter

class ProductsListFragment : Fragment(), OnClickProducts, OnClickCartItemCounter {
    private var mainActivityListener: MainActivityListener? = null
    private var _binding: FragmentProductListBinding? = null
    val binding: FragmentProductListBinding get() = _binding!!
    private val productListViewModel: ProductListViewModel by lazy {
        val viewModelFactory =
            ViewModelFactory {
                ProductListViewModel(
                    productRepository = ProductRepositoryImpl(),
                    shoppingCartRepository = ShoppingCartRepositoryImpl(requireContext()),
                    recentlyProductRepository = RecentlyProductRepositoryImpl(requireContext()),
                )
            }
        viewModelFactory.create(ProductListViewModel::class.java)
    }
    private lateinit var productAdapter: ProductAdapter
    private lateinit var recentlyAdapter: RecentlyAdapter

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
        binding.vm = productListViewModel
        binding.onClickProduct = this
        binding.lifecycleOwner = viewLifecycleOwner
        productAdapter =
            ProductAdapter(
                onClickProducts = this,
                onClickCartItemCounter = this,
            )
        binding.rvProducts.adapter = productAdapter
        recentlyAdapter =
            RecentlyAdapter(
                onClickProducts = this,
            )
        binding.horizontalView.rvRecentlyProduct.adapter = recentlyAdapter
    }

    private fun observeData() {
        productListViewModel.recentlyProducts.observe(viewLifecycleOwner) { recentlyData ->
            recentlyAdapter.updateProducts(recentlyData)
        }

        productListViewModel.products.observe(viewLifecycleOwner) { products ->
            productAdapter.updateProducts(addedProducts = products)
        }
        productListViewModel.productListEvent.observe(viewLifecycleOwner) { productListEvent ->
            when (productListEvent) {
                is ProductListEvent.DeleteProductEvent.Success -> {
                    requireContext().makeToast(
                        getString(R.string.delete_cart_item),
                    )
                    productAdapter.updateProduct(productListEvent.productId)
                }

                is ProductListEvent.UpdateProductEvent.Success -> {
                    productAdapter.updateProduct(productListEvent.productId)
                }
            }
        }
        productListViewModel.errorEvent.observe(viewLifecycleOwner) { errorState ->
            when (errorState) {
                ProductListEvent.LoadProductEvent.Fail ->
                    requireContext().makeToast(
                        getString(R.string.max_paging_data),
                    )

                ProductListEvent.ErrorEvent.NotKnownError ->
                    requireContext().makeToast(
                        getString(R.string.error_default),
                    )

                ProductListEvent.UpdateProductEvent.Fail,
                ProductListEvent.DeleteProductEvent.Fail,
                ->
                    requireContext()
                        .makeToast(
                            getString(R.string.error_update_cart_item),
                        )
            }
        }
        mainActivityListener?.observeProductList { updatedProducts ->
            productListViewModel.updateProducts(updatedProducts)
        }
        mainActivityListener?.observeRecentlyProduct {
            productListViewModel.loadPagingRecentlyProduct()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mainActivityListener = null
    }

    override fun clickProductItem(productId: Long) {
        val productFragment =
            ProductDetailFragment().apply {
                arguments = ProductDetailFragment.createBundle(productId)
            }
        mainActivityListener?.changeFragment(productFragment)
    }

    override fun clickShoppingCart() {
        val shoppingCartFragment = ShoppingCartFragment()
        mainActivityListener?.changeFragment(shoppingCartFragment)
    }

    override fun clickLoadPagingData() {
        loadPagingData()
    }

    override fun clickRecentlyItem(recentlyProduct: RecentlyProduct) {
        val productFragment =
            ProductDetailFragment().apply {
                arguments = ProductDetailFragment.createBundle(recentlyProduct.productId)
            }
        mainActivityListener?.changeFragment(productFragment)
    }

    private fun loadPagingData() {
        productListViewModel.loadPagingProduct()
    }

    override fun clickIncrease(product: Product) {
        productListViewModel.increaseShoppingCart(product)
    }

    override fun clickDecrease(product: Product) {
        productListViewModel.decreaseShoppingCart(product)
    }
}
