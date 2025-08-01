package com.project.compose.core.common.ui.component

import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import com.project.compose.core.common.ui.theme.Dimens.Dp2
import com.project.compose.core.common.ui.theme.Dimens.Dp8
import com.project.compose.core.common.ui.theme.Dimens.Dp80
import com.project.compose.core.common.ui.theme.PokeTheme.typography

@Composable
fun PokeCard(
    id: Int,
    name: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(Dp8),
        elevation = cardElevation(defaultElevation = Dp2),
        colors = cardColors(containerColor = White)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = spacedBy(Dp8),
            horizontalAlignment = CenterHorizontally
        ) {
            PokeImage(
                modifier = Modifier.size(Dp80),
                id = id
            )
            Text(
                text = name,
                style = typography.bodyBold1
            )
        }
    }
}