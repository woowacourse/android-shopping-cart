package woowacourse.shopping.presentation.goods.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityGoodsBinding
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.presentation.goods.detail.GoodsDetailActivity
import woowacourse.shopping.presentation.shoppingcart.ShoppingCartActivity

class GoodsActivity : AppCompatActivity() {
    private val binding: ActivityGoodsBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_goods)
    }
    private val viewModel: GoodsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpScreen()

        val adapter = GoodsAdapter { goods -> navigateToDetail(goods) }
        setUpGoodsList(adapter)
        setLoadButtonClickListener()

        viewModel.goods.observe(this) { goods ->
            adapter.updateItems(goods)
        }
    }

    private fun setUpScreen() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setUpGoodsList(adapter: GoodsAdapter) {
        binding.rvGoodsList.apply {
            this.adapter = adapter
            layoutManager = GridLayoutManager(this@GoodsActivity, 2)
            addOnScrollListener(
                object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(
                        recyclerView: RecyclerView,
                        newState: Int,
                    ) {
                        if (!binding.rvGoodsList.canScrollVertically(1) && viewModel.canLoadMore()) {
                            binding.btnLoadMore.visibility = View.VISIBLE
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

    private fun setLoadButtonClickListener() {
        binding.btnLoadMore.setOnClickListener {
            binding.btnLoadMore.visibility = View.GONE
            viewModel.addGoods()
        }
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
}
