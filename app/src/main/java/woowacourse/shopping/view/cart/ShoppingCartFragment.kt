package woowacourse.shopping.view.cart

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.shopping.R
import woowacourse.shopping.data.repository.ShoppingCartRepositoryImpl
import woowacourse.shopping.data.repository.ShoppingCartRepositoryImpl.Companion.CART_ITEM_LOAD_PAGING_SIZE
import woowacourse.shopping.databinding.FragmentShoppingCartBinding
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.CartItemCounter.Companion.DEFAULT_ITEM_COUNT
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.utils.ShoppingUtils.makeToast
import woowacourse.shopping.view.MainActivityListener
import woowacourse.shopping.view.ViewModelFactory
import woowacourse.shopping.view.cart.adapter.ShoppingCartAdapter
import woowacourse.shopping.view.cartcounter.OnClickCartItemCounter
import woowacourse.shopping.view.detail.ProductDetailFragment

class ShoppingCartFragment : Fragment(), OnClickShoppingCart, OnClickCartItemCounter {
    private var mainActivityListener: MainActivityListener? = null
    private var _binding: FragmentShoppingCartBinding? = null
    val binding: FragmentShoppingCartBinding get() = _binding!!

    private val shoppingCartViewModel: ShoppingCartViewModel by lazy {
        val viewModelFactory =
            ViewModelFactory { ShoppingCartViewModel(ShoppingCartRepositoryImpl(context = requireContext())) }
        viewModelFactory.create(ShoppingCartViewModel::class.java)
    }
    private lateinit var adapter: ShoppingCartAdapter

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
        _binding = FragmentShoppingCartBinding.inflate(inflater, container, false)
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
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = shoppingCartViewModel
        shoppingCartViewModel.loadPagingCartItemList(CART_ITEM_LOAD_PAGING_SIZE)
        binding.onClickShoppingCart = this
        adapter =
            ShoppingCartAdapter(
                onClickShoppingCart = this,
                onClickCartItemCounter = this,
                loadLastItem = {
                    shoppingCartViewModel.loadPagingCartItemList(CART_ITEM_LOAD_PAGING_SIZE)
                },
            )
        binding.rvShoppingCart.adapter = adapter
    }

    private fun observeData() {
        shoppingCartViewModel.shoppingCart.cartItems.observe(viewLifecycleOwner) {
            updateRecyclerView()
        }
        shoppingCartViewModel.shoppingCartEvent.observe(viewLifecycleOwner) { cartState ->
            when (cartState) {
                is ShoppingCartEvent.UpdateProductEvent.Success -> {
                    adapter.updateCartItem(cartState.productId)

                    mainActivityListener?.saveUpdateProduct(
                        cartState.productId,
                        cartState.count,
                    )
                }

                is ShoppingCartEvent.UpdateProductEvent.DELETE -> {
                    adapter.deleteCartItem(cartState.productId)

                    mainActivityListener?.saveUpdateProduct(
                        cartState.productId,
                        DEFAULT_ITEM_COUNT,
                    )

                    requireContext().makeToast(
                        getString(
                            R.string.delete_cart_item,
                        ),
                    )
                }
            }
        }

        shoppingCartViewModel.errorEvent.observe(viewLifecycleOwner) { errorState ->
            when (errorState) {
                ShoppingCartEvent.DeleteShoppingCart.Fail ->
                    requireContext().makeToast(
                        getString(
                            R.string.error_delete_data,
                        ),
                    )

                ShoppingCartEvent.LoadCartItemList.Fail ->
                    requireContext().makeToast(
                        getString(R.string.max_paging_data),
                    )

                ShoppingCartEvent.ErrorState.NotKnownError ->
                    requireContext().makeToast(
                        getString(R.string.error_default),
                    )

                ShoppingCartEvent.UpdateProductEvent.Fail ->
                    requireContext().makeToast(
                        getString(R.string.error_update_cart_item),
                    )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mainActivityListener = null
    }

    override fun clickBack() {
        mainActivityListener?.popFragment()
    }

    override fun clickCartItem(productId: Long) {
        val productFragment =
            ProductDetailFragment().apply {
                arguments = ProductDetailFragment.createBundle(productId)
            }
        mainActivityListener?.changeFragment(productFragment)
    }

    override fun clickRemoveCartItem(cartItem: CartItem) {
        shoppingCartViewModel.deleteShoppingCartItem(
            cartItemId = cartItem.id,
            productId = cartItem.product.id,
        )
    }

    override fun clickPrevPage() {
        if (shoppingCartViewModel.isExistPrevPage()) {
            shoppingCartViewModel.decreaseCurrentPage()
            updateRecyclerView()
        }
    }

    override fun clickNextPage() {
        if (shoppingCartViewModel.isExistNextPage()) {
            shoppingCartViewModel.increaseCurrentPage()
            updateRecyclerView()
        } else {
            requireContext().makeToast(
                getString(R.string.max_paging_data),
            )
        }
    }

    private fun updateRecyclerView() {
        val updatePageData = shoppingCartViewModel.getUpdatePageData()
        val isLastItem = shoppingCartViewModel.hasLastItem()
        adapter.updateCartItems(isLastItem, updatePageData)
        updateImageButtonColor()
    }

    private fun updateImageButtonColor() {
        binding.onPrevButton = shoppingCartViewModel.isExistPrevPage()
        binding.onNextButton = shoppingCartViewModel.isExistNextPage()
    }

    override fun clickIncrease(product: Product) {
        shoppingCartViewModel.increaseCartItem(product)
    }

    override fun clickDecrease(product: Product) {
        shoppingCartViewModel.decreaseCartItem(product)
    }
}
