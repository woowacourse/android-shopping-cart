package woowacourse.shopping.presentation.goods.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityGoodsBinding
import woowacourse.shopping.domain.model.ShoppingCartItem
import woowacourse.shopping.presentation.BaseActivity
import woowacourse.shopping.presentation.goods.detail.GoodsDetailActivity
import woowacourse.shopping.presentation.shoppingcart.ShoppingCartActivity
import woowacourse.shopping.presentation.util.QuantityClickListener
import woowacourse.shopping.presentation.util.ShoppingCartEvent

class GoodsActivity : BaseActivity() {
    private val binding by bind<ActivityGoodsBinding>(R.layout.activity_goods)
    private val viewModel: GoodsViewModel by viewModels { GoodsViewModel.FACTORY }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpScreen(binding.root)
        setUpBinding()
        setUpGoodsList()
        observeEvent()
    }

    override fun onStart() {
        super.onStart()
        viewModel.initGoods()
    }

    private fun setUpBinding() {
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@GoodsActivity
        }
    }

    private fun setUpGoodsList() {
        binding.rvGoodsList.apply {
            GoodsAdapter(
                quantityClickListener = object : QuantityClickListener {
                    override fun increase(item: ShoppingCartItem) {
                        viewModel.increaseQuantity(item)
                    }

                    override fun decrease(item: ShoppingCartItem) {
                        viewModel.decreaseQuantity(item)
                    }
                },
                goodsClickListener = ::navigateToDetail
            ).also { this.adapter = it }
            layoutManager = GridLayoutManager(this@GoodsActivity, SPAN_COUNT)
            addOnScrollListener(
                object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(
                        recyclerView: RecyclerView,
                        newState: Int,
                    ) {
                        val canScroll = recyclerView.canScrollVertically(SCROLL_DIRECTION)
                        viewModel.determineLoadMoreVisibility(canScroll)
                    }
                },
            )
        }
    }

    private fun observeEvent() {
        viewModel.shoppingCartEvent.observe(this) { event ->
            when (event) {
                ShoppingCartEvent.FAILURE -> {
                    Toast.makeText(this, getString(R.string.text_save_failure), Toast.LENGTH_SHORT).show()
                }
                ShoppingCartEvent.SUCCESS -> {}
            }
        }
    }

    private fun navigateToDetail(item: ShoppingCartItem) {
        val intent = GoodsDetailActivity.newIntent(this@GoodsActivity, item.goods.id)
        startActivity(intent)
    }

    private fun navigateToShoppingCart() {
        val intent = ShoppingCartActivity.newIntent(this)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.goods_list_action_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cart -> {
                navigateToShoppingCart()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val SPAN_COUNT: Int = 2
        private const val SCROLL_DIRECTION: Int = 1
    }
}
