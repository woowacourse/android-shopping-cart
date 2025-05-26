package woowacourse.shopping.feature.goods

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.application.ShoppingApplication
import woowacourse.shopping.databinding.ActivityGoodsBinding
import woowacourse.shopping.databinding.MenuCartActionViewBinding
import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.feature.goods.adapter.GoodsAdapter
import woowacourse.shopping.feature.goods.adapter.GoodsClickListener
import woowacourse.shopping.feature.goods.adapter.GoodsSpanSizeLookup
import woowacourse.shopping.feature.goodsdetails.GoodsDetailsActivity
import woowacourse.shopping.feature.model.ResultCode
import woowacourse.shopping.util.toUi

class GoodsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGoodsBinding
    private lateinit var adapter: GoodsAdapter
    private val viewModel: GoodsViewModel by viewModels {
        (application as ShoppingApplication).goodsFactory
    }
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoodsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupAdapter()
        setupRecyclerView()
        observeViewModel()
        setupActivityResultLauncher()
        observeCartInsertResult()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.nav_cart, menu)
        val menuItem = menu.findItem(R.id.nav_cart)
        val binding = MenuCartActionViewBinding.inflate(layoutInflater)
        menuItem.actionView = binding.root

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.root.setOnClickListener {
            onOptionsItemSelected(menuItem)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_cart -> {
                val intent = Intent(this, CartActivity::class.java)
                activityResultLauncher.launch(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecyclerView() {
        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.spanSizeLookup = GoodsSpanSizeLookup(adapter)
        binding.rvGoods.layoutManager = gridLayoutManager
        binding.rvGoods.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.hasNextPage.observe(this) { hasNext ->
            adapter.setHasNextPage(hasNext)
        }
        viewModel.navigateToCart.observe(this) { cart ->
            navigate(cart)
        }
    }

    private fun observeCartInsertResult() {
        viewModel.isSuccess.observe(this) { cart ->
            Toast.makeText(this, R.string.goods_detail_cart_insert_success_toast_message, Toast.LENGTH_SHORT).show()
        }
        viewModel.isFail.observe(this) {
            Toast.makeText(this, R.string.goods_detail_cart_insert_fail_toast_message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupAdapter() {
        adapter =
            GoodsAdapter(
                object : GoodsClickListener {
                    override fun onClickGoods(cart: Cart) {
                        navigate(cart)
                    }

                    override fun onClickHistory(cart: Cart) {
                        viewModel.findCartFromHistory(cart)
                    }

                    override fun insertToCart(cart: Cart) {
                        viewModel.insertToCart(cart)
                    }

                    override fun removeFromCart(cart: Cart) {
                        viewModel.removeFromCart(cart)
                    }

                    override fun loadMore() {
                        viewModel.addPage()
                    }
                },
            )
    }

    private fun setupActivityResultLauncher() {
        activityResultLauncher =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult(),
            ) { result ->
                when (result.resultCode) {
                    ResultCode.GOODS_DETAIL_INSERT.code,
                    ResultCode.CART_INSERT.code,
                    -> {
                        handleActivityResult(result.data)
                    }
                }
            }
    }

    private fun handleActivityResult(data: Intent?) {
        val changedId = data?.getLongExtra("GOODS_ID", 0) ?: 0
        val changedQuantity = data?.getIntExtra("GOODS_QUANTITY", 0) ?: 0
        viewModel.updateItemQuantity(changedId, changedQuantity)
        viewModel.refreshHistoryOnly()
    }

    private fun navigate(cart: Cart) {
        val intent = GoodsDetailsActivity.newIntent(this, cart.toUi())
        activityResultLauncher.launch(intent)
    }
}
