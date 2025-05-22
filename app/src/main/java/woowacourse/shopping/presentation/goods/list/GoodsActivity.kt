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

        val goodsAdapter = makeAdapter()
        setUpGoodsList(goodsAdapter)

        val latestGoodsAdapter =
            LatestGoodsAdapter { selectedGoodsID ->
                viewModel.moveToDetail(selectedGoodsID) { goodsId, lastGoodsId ->
                    navigateToDetail(goodsId, lastGoodsId)
                }
            }
        setUpLatestGoodsList(latestGoodsAdapter)

        setSupportActionBar(binding.toolbar)
        setUpObserver(goodsAdapter, latestGoodsAdapter)
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
                override fun onGoodsClick(selectedGoodsId: Int) {
                    viewModel.moveToDetail(selectedGoodsId) { goodsId, lastGoodsId ->
                        navigateToDetail(goodsId, lastGoodsId)
                    }
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
            layoutManager = GridLayoutManager(this@GoodsActivity, GOODS_SPAN_COUNT)
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

    private fun setUpLatestGoodsList(adapter: LatestGoodsAdapter) {
        binding.rvLatestGoodsList.apply {
            this.adapter = adapter
            layoutManager = GridLayoutManager(this@GoodsActivity, LATEST_GOODS_SPAN_COUNT, GridLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setUpObserver(
        goodsAdapter: GoodsAdapter,
        latestGoodsAdapter: LatestGoodsAdapter,
    ) {
        viewModel.goods.observe(this) { goods ->
            goodsAdapter.loadMoreItems(goods)
        }

        viewModel.onQuantityChanged.observe(this) { goods ->
            goods.forEach { goodsAdapter.notifyItemChanged(it) }
        }

        viewModel.shouldNavigateToShoppingCart.observe(this) {
            val intent = ShoppingCartActivity.newIntent(this)
            startActivity(intent)
        }

        viewModel.latestGoods.observe(this) {
            latestGoodsAdapter.addLatestGoods(it)
        }
    }

    private fun navigateToDetail(
        goodsId: Int,
        lastGoodsId: Int?,
    ) {
        val intent = GoodsDetailActivity.newIntent(this@GoodsActivity, goodsId, lastGoodsId)
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
        private const val GOODS_SPAN_COUNT: Int = 2
        private const val LATEST_GOODS_SPAN_COUNT: Int = 1
        private const val SCROLL_DIRECTION: Int = 1
    }
}
