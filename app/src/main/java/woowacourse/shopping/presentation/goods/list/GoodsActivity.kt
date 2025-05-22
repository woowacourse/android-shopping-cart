package woowacourse.shopping.presentation.goods.list

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.databinding.ActivityGoodsBinding
import woowacourse.shopping.databinding.MenuCartActionViewBinding
import woowacourse.shopping.presentation.BaseActivity
import woowacourse.shopping.presentation.goods.detail.GoodsDetailActivity
import woowacourse.shopping.presentation.model.GoodsUiModel
import woowacourse.shopping.presentation.shoppingcart.ShoppingCartActivity

class GoodsActivity : BaseActivity() {
    private val binding by bind<ActivityGoodsBinding>(R.layout.activity_goods)
    private val viewModel: GoodsViewModel by viewModels {
        GoodsViewModel.provideFactory(
            (application as ShoppingApplication).goodsRepository,
            (application as ShoppingApplication).shoppingRepository,
            (application as ShoppingApplication).latestGoodsRepository,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpScreen(binding.root)
        setUpBinding()

        val adapter = makeAdapter()
        setUpGoodsList(adapter)
        setUpObserver(adapter)

        setSupportActionBar(binding.toolbar)
    }

    override fun onStart() {
        super.onStart()

        viewModel.restoreGoods()
    }

    private fun setUpBinding() {
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    private fun makeAdapter(): GoodsAdapter {
        return GoodsAdapter(
            object : GoodsClickListener {
                override fun onGoodsClick(goods: GoodsUiModel) {
                    viewModel.updateLatestGoods(goods)
                    navigateToDetail(goods)
                }

                override fun onPlusClick(position: Int) {
                    viewModel.increaseGoodsCount(position)
                }

                override fun onIncreaseQuantity(position: Int) {
                    viewModel.increaseGoodsCount(position)
                }

                override fun onDecreaseQuantity(position: Int) {
                    viewModel.decreaseGoodsCount(position)
                }
            },
        )
    }

    private fun setUpGoodsList(adapter: GoodsAdapter) {
        binding.rvGoodsList.apply {
            this.adapter = adapter
            layoutManager = GridLayoutManager(this@GoodsActivity, SPAN_COUNT)
            addOnScrollListener(
                object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(
                        recyclerView: RecyclerView,
                        newState: Int,
                    ) {
                        if (!binding.rvGoodsList.canScrollVertically(SCROLL_DIRECTION)) {
                            viewModel.updateShouldShowLoadMore()
                        }
                    }
                },
            )
        }
    }

    private fun setUpObserver(adapter: GoodsAdapter) {
        viewModel.goods.observe(this) { goods ->
            adapter.loadMoreItems(goods)
        }

        viewModel.onQuantityChanged.observe(this) { goods ->
            goods.forEach { adapter.notifyItemChanged(it) }
        }

        viewModel.shouldNavigateToShoppingCart.observe(this) {
            val intent = ShoppingCartActivity.newIntent(this)
            startActivity(intent)
        }
    }

    private fun navigateToDetail(goods: GoodsUiModel) {
        val intent = GoodsDetailActivity.newIntent(this@GoodsActivity, goods.id)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.goods_list_action_bar, menu)
        val cartItem = menu?.findItem(R.id.action_cart)
        val menuBinding = MenuCartActionViewBinding.inflate(layoutInflater)
        cartItem?.actionView = menuBinding.root

        menuBinding.vm = viewModel
        menuBinding.lifecycleOwner = this

        return true
    }

    companion object {
        private const val SPAN_COUNT: Int = 2
        private const val SCROLL_DIRECTION: Int = 1
    }
}
