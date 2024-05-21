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
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.utils.ShoppingUtils.makeToast
import woowacourse.shopping.view.MainFragmentListener
import woowacourse.shopping.view.ViewModelFactory
import woowacourse.shopping.view.cart.adapter.ShoppingCartAdapter
import woowacourse.shopping.view.cartcounter.ChangeCartItemResultState
import woowacourse.shopping.view.cartcounter.OnClickCartItemCounter
import woowacourse.shopping.view.detail.ProductDetailFragment

class ShoppingCartFragment : Fragment(), OnClickShoppingCart, OnClickCartItemCounter {
    private var mainFragmentListener: MainFragmentListener? = null
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
        if (context is MainFragmentListener) {
            mainFragmentListener = context
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
        shoppingCartViewModel.errorState.observe(viewLifecycleOwner) { errorState ->
            when (errorState) {
                ShoppingCartState.DeleteShoppingCart.Fail ->
                    requireContext().makeToast(
                        getString(
                            R.string.error_delete_data,
                        ),
                    )

                ShoppingCartState.LoadCartItemList.Fail ->
                    requireContext().makeToast(
                        getString(R.string.max_paging_data),
                    )

                ShoppingCartState.ErrorState.NotKnownError ->
                    requireContext().makeToast(
                        getString(R.string.error_default),
                    )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mainFragmentListener = null
    }

    override fun clickBack() {
        mainFragmentListener?.popFragment()
    }

    override fun clickCartItem(productId: Long) {
        val productFragment =
            ProductDetailFragment().apply {
                arguments = ProductDetailFragment.createBundle(productId)
            }
        mainFragmentListener?.changeFragment(productFragment)
    }

    override fun clickRemoveCartItem(cartItemId: Long) {
        shoppingCartViewModel.deleteShoppingCartItem(cartItemId)
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
        product.cartItemCounter.selectItem()
        val resultState =  product.cartItemCounter.increase()
        when(resultState){
            ChangeCartItemResultState.Success -> {
                adapter.updateCartItem(product.id)
            }
            ChangeCartItemResultState.Fail -> {}
        }
    }

    override fun clickDecrease(product: Product) {
        val resultState = product.cartItemCounter.decrease()
        when(resultState){
            ChangeCartItemResultState.Success -> {
                adapter.updateCartItem(product.id)
            }
            ChangeCartItemResultState.Fail -> {

            }
        }
    }
}
