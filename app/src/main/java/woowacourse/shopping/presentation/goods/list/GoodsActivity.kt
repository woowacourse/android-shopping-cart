package woowacourse.shopping.presentation.goods.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.data.goods.repository.GoodsRepositoryImpl
import woowacourse.shopping.databinding.ActivityGoodsBinding
import woowacourse.shopping.presentation.BaseActivity
import woowacourse.shopping.presentation.goods.detail.GoodsDetailActivity
import woowacourse.shopping.presentation.model.GoodsUiModel
import woowacourse.shopping.presentation.shoppingcart.ShoppingCartActivity

class GoodsActivity : BaseActivity() {
    private val binding by bind<ActivityGoodsBinding>(R.layout.activity_goods)
    private val viewModel: GoodsViewModel by viewModels {
        GoodsViewModelFactory(GoodsRepositoryImpl())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpScreen(binding.root)
        setUpBinding()
        setUpGoodsList()
    }

    private fun setUpBinding() {
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@GoodsActivity
        }
    }

    private fun setUpGoodsList() {
        binding.rvGoodsList.apply {
            this.adapter = GoodsAdapter { goodsUiModel -> navigateToDetail(goodsUiModel) }
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

    private fun navigateToDetail(goodsUiModel: GoodsUiModel) {
        val intent = GoodsDetailActivity.newIntent(this@GoodsActivity, goodsUiModel)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.goods_list_action_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cart -> {
                val intent = ShoppingCartActivity.newIntent(this)
                startActivity(intent)
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
