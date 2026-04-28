package woowacourse.shopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import woowacourse.shopping.constants.MockData
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidshoppingTheme {
                ProductListScreen()
            }
        }
    }
}

@Composable
fun ProductListScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Box {
                Row {
                    Text(stringResource(R.string.app_name))
                    Icon(
                        painter = painterResource(R.drawable.cart_icon),
                        contentDescription = stringResource(R.string.cart_description),
                    )
                }
            }
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
        ) {
            ProductList(
                products = MockData.MOCK_PRODUCTS,
            )
        }
    }
}
