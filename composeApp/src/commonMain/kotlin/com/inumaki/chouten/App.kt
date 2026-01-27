package com.inumaki.chouten

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.inumaki.chouten.common.getFeatures
import com.inumaki.core.ui.AppScaffold
import com.inumaki.core.ui.model.AppConfig
import com.inumaki.core.ui.model.AppRoute
import com.inumaki.core.ui.model.DiscoverRoute
import com.inumaki.core.ui.model.FeatureEntry
import com.inumaki.core.ui.model.GlobalState
import com.inumaki.core.ui.model.HomeRoute
import com.inumaki.core.ui.model.NavigationScope
import com.inumaki.core.ui.model.PresentationStyle
import com.inumaki.core.ui.model.RepoRoute
import com.inumaki.core.ui.model.SettingsRoute
import com.inumaki.core.ui.model.presentationStyle
import com.inumaki.core.ui.theme.AppTheme
import com.inumaki.features.discover.DiscoverView
import com.inumaki.features.discover.DiscoverViewModel
import com.inumaki.features.home.HomeView
import com.inumaki.features.repo.RepoView
import dev.chouten.features.settings.SettingsView
import kotlin.math.roundToInt

fun NavBackStackEntry.toAppRoute(featureEntries: List<FeatureEntry>): AppRoute? {
    return featureEntries
        .asSequence()
        .mapNotNull { it.tryCreateRoute(this) }
        .firstOrNull()
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun App(provider: HeadingSource) {
    val navController = rememberNavController()
    val (featureEntries, uiConfigProviders) = getFeatures()
    val backStackEntry by navController.currentBackStackEntryAsState()

    val backStackEntries by navController.currentBackStack.collectAsState()


    val currentRoute: AppRoute? = backStackEntry?.let { entry ->
        featureEntries
            .asSequence()
            .mapNotNull { feature -> feature.tryCreateRoute(entry) }
            .firstOrNull()
    }

    val fullscreenRoute = backStackEntries
        .mapNotNull { entry -> entry.toAppRoute(featureEntries) }
        .lastOrNull { route ->
            route.presentationStyle() == PresentationStyle.Fullscreen
        }

    val topRoute = backStackEntries
        .lastOrNull()
        ?.toAppRoute(featureEntries)

    val visible = topRoute?.presentationStyle() == PresentationStyle.Sheet
    val density = LocalDensity.current

    val transition = updateTransition(
        targetState = visible,
        label = "ShowSheet"
    )

    val viewScale by transition.animateFloat(
        label = "ViewScale"
    ) { showSheet ->
        if (showSheet) 0.85f else 1f
    }
    val viewCornerRadius by transition.animateDp(
        label = "ViewCornerRadius"
    ) { showSheet ->
        if (showSheet) 20.dp else 0.dp
    }

    val navScope = remember { NavigationScope() }
    val discoverVm = navScope.viewModelStore.get("discover") { DiscoverViewModel() }

    LaunchedEffect(provider.heading) {
        provider.heading.collect { newValue ->
            GlobalState.setAngle(newValue)
        }
    }

    val appConfig = AppConfig(
        navController,
        navScope,
        DiscoverRoute,
        featureEntries,
        uiConfigProviders
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        AppTheme {
            AppScaffold(
                provider.heading,
                appConfig,
                modifier = Modifier
                    .scale(viewScale)
                    .clip(RoundedCornerShape(viewCornerRadius))
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    NavHost(
                        navController,
                        DiscoverRoute,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        composable<DiscoverRoute> { }
                        composable<HomeRoute> { }
                        composable<RepoRoute> { }
                        composable<SettingsRoute> { }
                    }

                    fullscreenRoute?.let { route ->
                        when (route) {
                            is DiscoverRoute -> DiscoverView(discoverVm)
                            is HomeRoute -> HomeView()
                            is RepoRoute -> RepoView()
                            else -> {}
                        }
                    }
                }
            }

            if (visible) {
                Box(
                    modifier = Modifier
                        .alpha(0.3f)
                        .fillMaxSize()
                        .background(Color.Black)
                        .clickable {
                            navController.popBackStack()
                        }
                )
            }

            BoxWithConstraints(Modifier.fillMaxSize()) {
                val density = LocalDensity.current
                val parentHeightPx = with(density) { maxHeight.toPx() }
                val sheetHeightPx = parentHeightPx * 0.9f

                val offsetY by animateFloatAsState(
                    targetValue = if (visible) 0f else sheetHeightPx,
                    animationSpec = spring(
                        dampingRatio = 0.85f,
                        stiffness = Spring.StiffnessMediumLow
                    ),
                    label = "sheetOffset"
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .fillMaxHeight(0.9f)
                        .offset { IntOffset(0, offsetY.roundToInt()) }
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                        .background(AppTheme.colors.container)
                ) {
                    SettingsView(appConfig)
                }
            }

        }
    }
}