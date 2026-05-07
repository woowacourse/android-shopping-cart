package woowacourse.shopping.ui.shopping.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import woowacourse.shopping.ProductFixture
import woowacourse.shopping.R
import woowacourse.shopping.domain.ProductWithQuantity
import woowacourse.shopping.ui.theme.topAppBarColor
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun ProductItem(
    productWithQuantity: ProductWithQuantity,
    onClick: () -> Unit,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RectangleShape,
        modifier = modifier.clickable { onClick() },
    ) {
        Column {
            Box {
                AsyncImage(
                    model = productWithQuantity.imageUrl,
                    contentDescription = productWithQuantity.productName,
                    modifier = Modifier.size(154.dp),
                )
                if (productWithQuantity.quantity == 0) {
                    Box(
                        modifier =
                            Modifier
                                .align(Alignment.BottomEnd)
                                .padding(8.dp)
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .clickable { onIncrease() },
                    ) {
                        Image(
                            modifier =
                                Modifier
                                    .size(20.dp)
                                    .align(Alignment.Center),
                            painter = painterResource(R.drawable.plus_icon),
                            contentDescription = "아이템 추가 버튼",
                        )
                    }
                }
                if (productWithQuantity.quantity > 0) {
                    Box(
                        modifier =
                            Modifier
                                .padding(vertical = 8.dp)
                                .align(Alignment.BottomCenter),
                    ) {
                        SelectItemCountBox(
                            count = productWithQuantity.quantity,
                            onIncrease = { onIncrease() },
                            onDecrease = { onDecrease() },
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
            Column(
                modifier = Modifier.padding(6.dp),
            ) {
                Text(
                    text = productWithQuantity.productName,
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = stringResource(R.string.price_format, productWithQuantity.price.value),
                    color = topAppBarColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                )
            }
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun ProductItemPreview() {
    val packageName = LocalContext.current.packageName

    val newProductWithQuantity1 = ProductWithQuantity(ProductFixture.productList(packageName).first(), 0)
    val newProductWithQuantity2 = ProductWithQuantity(ProductFixture.productList(packageName).first(), 1)
    Column {
        ProductItem(
            productWithQuantity = newProductWithQuantity1,
            onClick = {},
            onIncrease = {},
            onDecrease = {},
        )

        ProductItem(
            productWithQuantity = newProductWithQuantity2,
            onClick = {},
            onIncrease = {},
            onDecrease = {},
        )
    }
}
