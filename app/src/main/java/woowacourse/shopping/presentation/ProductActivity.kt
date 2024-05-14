package woowacourse.shopping.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductBinding
import woowacourse.shopping.presentation.base.BindingActivity


class ProductActivity :
    BindingActivity<ActivityProductBinding>(R.layout.activity_product) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActionBar()
    }

    private fun initActionBar() {
        supportActionBar?.apply {
            title = "꼬상"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.shopping_menu, menu)
        return true
    }
}
