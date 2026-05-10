@file:Suppress("FunctionName")

package woowacourse.shopping.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import woowacourse.shopping.R
import woowacourse.shopping.model.Quantity
import woowacourse.shopping.ui.DisplayText
import woowacourse.shopping.ui.WonMoney

@Composable
fun ShoppingCartItems(
    title: String,
    imageUrl: String,
    quantity: Int,
    onIncrementQuantity: () -> Unit,
    onnDecrementQuantity: () -> Unit,
    displayableMoney: DisplayText,
    onRemoveShoppingItemClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(4.dp),
                ).border(
                    color = MaterialTheme.colorScheme.outline,
                    width = 1.dp,
                    shape = RoundedCornerShape(4.dp),
                ).padding(12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Image(
                painter = painterResource(R.drawable.remove_icon),
                contentDescription = stringResource(R.string.remove_item_description),
                modifier =
                    Modifier
                        .size(13.dp)
                        .clickable { onRemoveShoppingItemClick() },
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            verticalAlignment = Alignment.Bottom,
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = stringResource(R.string.product_image_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                        .width(136.dp)
                        .height(72.dp)
                        .padding(bottom = 8.dp)
                        .background(MaterialTheme.colorScheme.surfaceContainer),
            )

            Spacer(modifier = Modifier.weight(1f))

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                NumberCounter(
                    count = quantity,
                    onIncrement = onIncrementQuantity,
                    onDecrement = onnDecrementQuantity,
                    modifier = Modifier.width(120.dp),
                )

                Text(
                    text = displayableMoney.display(),
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ShoppingCartItemsPreview() {
    ShoppingCartItems(
        title = "동원 스위트콘",
        imageUrl = "",
        displayableMoney = WonMoney(99_800),
        onRemoveShoppingItemClick = {},
        quantity = 1,
        onIncrementQuantity = {},
        onnDecrementQuantity = {  },
    )
}
