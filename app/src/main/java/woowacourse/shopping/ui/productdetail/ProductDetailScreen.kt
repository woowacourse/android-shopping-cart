package woowacourse.shopping.ui.productdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R
import woowacourse.shopping.ui.common.QuantityCounter
import woowacourse.shopping.ui.productlist.PreviewableAsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    imageUrl: String,
    title: String,
    price: String,
    quantity: Int,
    recentProductTitle: String,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onCloseClick: () -> Unit,
    onAddToCartClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        containerColor = Color.White,
        modifier = modifier.fillMaxSize(),
        topBar = {
            ProductAppBar(
                onCloseClick = onCloseClick,
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Column {
                PreviewableAsyncImage(
                    imageUrl = imageUrl,
                    description = title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
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
                        text = price,
                        fontWeight = FontWeight.W400,
                        fontSize = 20.sp,
                    )
                    QuantityCounter(
                        quantity = quantity,
                        onIncrement = onIncrement,
                        onDecrement = onDecrement,
                        modifier = Modifier
                            .width(126.dp)
                            .height(42.dp),
                    )
                }

                LatestProductItem(
                    title = title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 18.dp)
                        .align(Alignment.CenterHorizontally),
                )
            }
            CartPutButton(
                onClick = onAddToCartClick,
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }
}

@Composable
private fun LatestProductItem(
    title: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(4.dp),
                color = Color(0xFFAAAAAA),
            )
            .padding(vertical = 16.dp, horizontal = 18.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = stringResource(R.string.last_products_title),
                fontSize = 16.sp,
                fontWeight = W700,
                color = Color(0xFF04C09E),
            )

            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = W400,
            )
        }
    }
}

@Composable
private fun CartPutButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color(0xff04c09e))
            .clickable(onClick = onClick)
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
        onCloseClick = {},
        onAddToCartClick = {},
        quantity = 1,
        onIncrement = {},
        onDecrement = {},
        recentProductTitle = "최근 본 상품",
    )
}
