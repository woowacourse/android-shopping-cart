package woowacourse.shopping

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import woowacourse.shopping.ui.screens.product.ProductActivity

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, ProductActivity::class.java)
        startActivity(intent)

        finish()
    }
}
