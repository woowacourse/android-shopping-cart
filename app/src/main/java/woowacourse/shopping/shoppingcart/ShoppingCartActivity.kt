package woowacourse.shopping.shoppingcart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.productlist.ProductListActivity
import woowacourse.shopping.shoppingcart.uimodel.LoadCartItemState
import woowacourse.shopping.shoppingcart.uimodel.ShoppingCartClickAction
import woowacourse.shopping.util.ViewModelFactory
import woowacourse.shopping.util.showToastMessage

class ShoppingCartActivity : AppCompatActivity(), ShoppingCartClickAction {
    private lateinit var binding: ActivityShoppingCartBinding
    private lateinit var adapter: ShoppingCartAdapter
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    private val viewModel: ShoppingCartViewModel by viewModels { ViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        attachAdapter()

        initShoppingCart()
        onBackPressedCallbackInit()

        updateView()
    }

    private fun attachAdapter() {
        adapter = ShoppingCartAdapter(this)
        binding.rcvShoppingCart.adapter = adapter
        binding.rcvShoppingCart.itemAnimator = null
    }

    private fun initShoppingCart() {
        viewModel.loadCartItems()
        viewModel.updatePageCount()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Cart"
    }

    private fun updateView() {
        viewModel.loadState.observe(this) { state ->
            when (state) {
                is LoadCartItemState.AddNextPageOfItem -> adapter.addItem(state.result)
                is LoadCartItemState.InitView -> adapter.replaceItems(state.currentCartItems)
                is LoadCartItemState.DeleteCartItem -> adapter.deleteItemByProductId(state.result)
                is LoadCartItemState.ChangeItemCount -> adapter.changeProductInfo(state.result)
                is LoadCartItemState.MinusFail -> showToastMessage(R.string.min_cart_item_message)
                is LoadCartItemState.PlusFail -> showToastMessage(R.string.max_cart_item_message)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            setResultAndFinish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemRemoveBtnClicked(id: Long) {
        viewModel.deleteCartItem(id)
    }

    override fun onPlusCountClicked(id: Long) {
        viewModel.plusCartItemCount(id)
    }

    override fun onMinusCountClicked(id: Long) {
        viewModel.minusCartItemCount(id)
    }

    private fun onBackPressedCallbackInit() {
        onBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    setResultAndFinish()
                }
            }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun setResultAndFinish() {
        val intent =
            ProductListActivity.changedProductIntent(
                this@ShoppingCartActivity,
                viewModel.changedProductIds.toLongArray(),
            )
        if (viewModel.changedProductIds.isEmpty()) {
            setResult(RESULT_CANCELED, intent)
            finish()
        } else {
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    companion object {
        fun newInstance(context: Context) = Intent(context, ShoppingCartActivity::class.java)
    }
}
