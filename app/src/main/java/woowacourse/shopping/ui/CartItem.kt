package woowacourse.shopping.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import woowacourse.shopping.domain.ProductAndCount
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.ui.theme.topAppBarColor
import woowacourse.shopping.util.intFormatter
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun CartItem(
    productAndCount: ProductAndCount,
    modifier: Modifier = Modifier
) {

    Card(
        colors =
            CardDefaults.cardColors(
                containerColor = Color.White,
            ),
        shape = RectangleShape,
        modifier = modifier.padding(18.dp),
        border = BorderStroke(
            width = 1.dp,
            color = topAppBarColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = productAndCount.productName,
                    fontWeight = FontWeight.W700,
                    fontSize = 18.sp,
                    color = topAppBarColor
                )
                Image(
                    painter = painterResource(id = R.drawable.x_icon),
                    contentDescription = "xButton",
                    modifier =
                        Modifier
                            .size(16.dp)
                            .clickable {
                                CartRepository.deleteProduct(productId = productAndCount.productId)
                            },
                    colorFilter = ColorFilter.tint(topAppBarColor)
                )
            }
            Spacer(modifier= Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                AsyncImage(
                    model = productAndCount.product.imageUrl,
                    contentDescription = productAndCount.product.productName,
                    modifier = Modifier.size(136.dp,72.dp),
                )
                Box{
                    Text(
                        modifier = Modifier.align(Alignment.BottomEnd),
                        text = "${intFormatter(productAndCount.product.price.value)}원",
                        fontWeight = FontWeight.W400,
                        fontSize = 16.sp,
                        color = topAppBarColor,

                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun CartItemPreview() {
    CartItem(
        productAndCount = ProductAndCount(
            Product(
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image7}",
                productName = "[든든] 동원 스위트콘",
                price = Price(99800),
            ),
            count = 1
        ),
    )
}