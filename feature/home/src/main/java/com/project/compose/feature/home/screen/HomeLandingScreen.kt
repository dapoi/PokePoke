package com.project.compose.feature.home.screen

import android.Manifest.permission.POST_NOTIFICATIONS
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.TIRAMISU
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells.Adaptive
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.project.compose.core.common.R
import com.project.compose.core.common.base.BaseScreen
import com.project.compose.core.common.ui.component.PokeCard
import com.project.compose.core.common.ui.component.SearchBar
import com.project.compose.core.common.ui.component.attr.AppTopBarAttr.ActionMenu
import com.project.compose.core.common.ui.component.attr.AppTopBarAttr.TopBarArgs
import com.project.compose.core.common.ui.theme.Dimens.Dp100
import com.project.compose.core.common.ui.theme.Dimens.Dp120
import com.project.compose.core.common.ui.theme.Dimens.Dp16
import com.project.compose.core.common.ui.theme.Dimens.Dp8
import com.project.compose.core.common.ui.theme.PokeTheme.typography
import com.project.compose.core.common.utils.state.collectAsStateValue
import com.project.compose.feature.home.viewmodel.HomeLandingViewModel

@Composable
internal fun HomeLandingScreen(
    navController: NavController,
    viewModel: HomeLandingViewModel = hiltViewModel()
) = with(viewModel) {
    val notifPermission = rememberLauncherForActivityResult(RequestPermission()) {}
    val query = searchQuery.collectAsStateValue()
    val isSearchActive = isSearchActive.collectAsStateValue()
    val lazyPokemonItems = viewModel.pokemons.collectAsLazyPagingItems()

    val topBarContent: @Composable () -> Unit = {
        AnimatedContent(
            targetState = isSearchActive,
            transitionSpec = {
                val enterTransition = fadeIn(animationSpec = tween(220, delayMillis = 90)) +
                        slideInVertically(
                            initialOffsetY = { -it },
                            animationSpec = tween(220, delayMillis = 90)
                        )
                val exitTransition = fadeOut(animationSpec = tween(90)) +
                        slideOutVertically(targetOffsetY = { -it }, animationSpec = tween(90))
                (enterTransition togetherWith exitTransition).using(SizeTransform(clip = true))
            },
            label = "Search Bar Animation"
        ) { isSearching ->
            if (isSearching) {
                SearchBar(
                    query = query,
                    onQueryChanged = ::onSearchQueryChanged
                )
            } else Text(
                text = "Pokémon Catalog",
                style = typography.h3.copy(fontWeight = Medium)
            )
        }
    }

    BaseScreen(
        topBarArgs = TopBarArgs(
            title = topBarContent,
            iconBack = if (isSearchActive) R.drawable.ic_back else null,
            actionMenus = if (isSearchActive.not()) listOf(
                ActionMenu(
                    icon = R.drawable.ic_search,
                    nameIcon = "Search",
                    onClickActionMenu = { toggleSearchQuery() }
                )
            ) else emptyList(),
            onClickBack = { toggleSearchQuery() }
        )
    ) {
        LaunchedEffect(Unit) {
            if (SDK_INT >= TIRAMISU) notifPermission.launch(POST_NOTIFICATIONS)
        }

        when (val refreshState = lazyPokemonItems.loadState.refresh) {
            is LoadState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(CenterHorizontally)
                    )
                }
            }

            is LoadState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
                    Column(horizontalAlignment = CenterHorizontally) {
                        Text(
                            text = "Failed to fetch data: ${refreshState.error.localizedMessage}",
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(Dp8))
                        Button(onClick = { lazyPokemonItems.retry() }) {
                            Text("Retry")
                        }
                    }
                }
            }

            else -> if (lazyPokemonItems.itemCount == 0) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Center
                ) {
                    Text(
                        text = "Pokémon not found",
                        style = typography.bodyBold1
                    )
                }
            } else LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = Adaptive(minSize = Dp120),
                verticalArrangement = spacedBy(Dp8),
                horizontalArrangement = spacedBy(Dp8),
                contentPadding = PaddingValues(bottom = Dp100)
            ) {
                items(
                    count = lazyPokemonItems.itemCount,
                    key = lazyPokemonItems.itemKey { it.id }
                ) { index ->
                    val pokemon = lazyPokemonItems[index]
                    pokemon?.let {
                        PokeCard(
                            modifier = Modifier.fillMaxWidth(),
                            id = pokemon.id,
                            name = pokemon.name
                        )
                    }
                }

                item(span = { GridItemSpan(maxLineSpan) }) {
                    when (lazyPokemonItems.loadState.append) {
                        is LoadState.Loading -> {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(Dp16),
                                horizontalArrangement = Arrangement.Center
                            ) { CircularProgressIndicator() }
                        }

                        is LoadState.Error -> {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = CenterHorizontally
                            ) {
                                Text("Failed to load more")
                                Button(onClick = { lazyPokemonItems.retry() }) {
                                    Text("Try Again")
                                }
                            }
                        }

                        else -> Unit
                    }
                }
            }
        }
    }
}