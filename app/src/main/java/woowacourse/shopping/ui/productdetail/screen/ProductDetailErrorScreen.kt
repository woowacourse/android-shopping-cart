package woowacourse.shopping.ui.productdetail.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.shopping.ui.productdetail.component.ProductDetailTopAppBar
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailErrorScreen(
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = { ProductDetailTopAppBar(onClose) },
        containerColor = Color.White,
    ) { innerPadding ->
        Box(
            modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Text(
                text = "상품 정보를 불러오는데 실패했습니다.",
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun ProductDetailErrorScreenPreview() {
    ProductDetailErrorScreen(onClose = {})
}
