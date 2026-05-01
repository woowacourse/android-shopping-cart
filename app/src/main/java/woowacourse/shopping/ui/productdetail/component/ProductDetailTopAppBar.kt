package woowacourse.shopping.ui.productdetail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.R
import woowacourse.shopping.ui.theme.topAppBarColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailTopAppBar(onClose: () -> Unit) {
    TopAppBar(
        title = {},
        actions = {
            Image(
                painter = painterResource(id = R.drawable.x_icon),
                contentDescription = "xButton",
                modifier =
                    Modifier
                        .padding(26.dp)
                        .size(16.dp)
                        .clickable { onClose() },
            )
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = topAppBarColor,
                titleContentColor = Color.White,
            ),
    )
}

@Preview
@Composable
private fun ProductDetailTopAppBarPreview() {
    ProductDetailTopAppBar(onClose = {})
}
