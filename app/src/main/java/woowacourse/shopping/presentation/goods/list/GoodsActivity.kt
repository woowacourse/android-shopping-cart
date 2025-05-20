package woowacourse.shopping.presentation.goods.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.databinding.ActivityGoodsBinding
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.presentation.BaseActivity
import woowacourse.shopping.presentation.goods.detail.GoodsDetailActivity
import woowacourse.shopping.presentation.shoppingcart.ShoppingCartActivity

class GoodsActivity : BaseActivity() {
    private val binding by bind<ActivityGoodsBinding>(R.layout.activity_goods)
    private val viewModel: GoodsViewModel by viewModels {
        GoodsViewModel.provideFactory((application as ShoppingApplication).goodsRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpScreen(binding.root)

        binding.vm = viewModel
        binding.lifecycleOwner = this

        val adapter = GoodsAdapter { goods -> navigateToDetail(goods) }
        setUpGoodsList(adapter)

        viewModel.goods.observe(this) { goods ->
            adapter.updateItems(goods)
        }
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

    private fun navigateToDetail(goods: Goods) {
        val intent = GoodsDetailActivity.newIntent(this@GoodsActivity, goods)
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
