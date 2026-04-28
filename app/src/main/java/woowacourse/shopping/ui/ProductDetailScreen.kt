package woowacourse.shopping.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import woowacourse.shopping.R
import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product
import woowacourse.shopping.ui.theme.buttonColor
import woowacourse.shopping.ui.theme.dividerColor
import woowacourse.shopping.ui.theme.topAppBarColor
import woowacourse.shopping.util.intFormatter
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    product: Product,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
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
                                .clickable { onClose() }
                    )
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = topAppBarColor,
                        titleContentColor = Color.White,
                    ),
            )
        },
        containerColor = Color.White,
    ) { innerPadding ->
        Box(
            modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(modifier = modifier.fillMaxWidth()) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.productName,
                    modifier = Modifier.fillMaxWidth(),
                )
                Box(modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp)) {
                    Text(
                        text = product.productName,
                        fontWeight = FontWeight.W700,
                        fontSize = 24.sp,
                        color = Color.Black
                    )
                }
                HorizontalDivider(color = dividerColor, thickness = 1.dp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "가격",
                        fontWeight = FontWeight.W400,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "${intFormatter(product.price.value)}원",
                        fontWeight = FontWeight.W400,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                }
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                onClick = {},
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor
                )
            ) {
                Text(
                    text = "장바구니 담기",
                    fontWeight = FontWeight.W700,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun ProductDetailScreenPreview() {
    ProductDetailScreen(
        product = Product(
            imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image7}",
            productName = "[든든] 동원 스위트콘",
            price = Price(99800),
        ),
        onClose = {}
    )
}