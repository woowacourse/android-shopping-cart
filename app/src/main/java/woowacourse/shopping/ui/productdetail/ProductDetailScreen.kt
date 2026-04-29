package woowacourse.shopping.ui.productlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R
import woowacourse.shopping.ui.productdetail.ProductAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    imageUrl: String,
    title: String,
    price: String,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            ProductAppBar()
        },
        bottomBar = {
            CartPutButton()
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
        ) {
            Column {
                PreviewableAsyncImage(
                    imageUrl = imageUrl,
                    description = title,
                    modifier = Modifier.fillMaxWidth(),
                )
                Text(
                    text = title,
                    fontWeight = FontWeight.W700,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 18.dp),
                )
                HorizontalDivider()
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 18.dp),
                ) {
                    Text(
                        text = stringResource(R.string.product_detail_price),
                        fontWeight = FontWeight.W400,
                        fontSize = 20.sp,
                    )
                    Text(
                        text = price,
                        fontWeight = FontWeight.W400,
                        fontSize = 20.sp,
                    )
                }
            }
        }
    }
}

@Composable
private fun CartPutButton(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color(0xff04c09e))
            .padding(vertical = 12.dp),
    ) {
        Text(
            stringResource(R.string.product_detail_select),
            fontWeight = FontWeight.W700,
            fontSize = 24.sp,
            color = Color.White,
        )
    }
}

@Preview
@Composable
private fun ProductScreenPreview() {
    ProductDetailScreen(
        imageUrl = "",
        title = "프리뷰",
        price = "1,000원",
    )
}
