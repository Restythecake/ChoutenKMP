package com.inumaki.core.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.inumaki.core.ui.components.AppBottomBar
import com.inumaki.core.ui.components.AppTopBar
import com.inumaki.core.ui.model.AppConfig
import com.inumaki.core.ui.model.AppRoute
import com.inumaki.core.ui.model.NavigationScope
import com.inumaki.core.ui.theme.AppTheme
import kotlinx.coroutines.flow.StateFlow

@Composable
fun AppScaffold(heading: StateFlow<Float>, appConfig: AppConfig) {
    val angle = heading.collectAsState()

    val backStackEntry by appConfig.navController.currentBackStackEntryAsState()

    val currentRoute: AppRoute? = backStackEntry?.let { entry ->
        appConfig.featureEntries
            .asSequence()
            .mapNotNull { feature -> feature.tryCreateRoute(entry) }
            .firstOrNull()
    }

    val topConfig = currentRoute?.let { route ->
        appConfig.uiConfigProvider.asSequence().mapNotNull { it.topBarConfig(route = route, navController = appConfig.navController) }
            .firstOrNull()
    }

    AppTheme() {
        Scaffold(
            topBar = { AppTopBar(topConfig, angle.value) },
            bottomBar = { AppBottomBar(angle.value, appConfig.navController) },
            containerColor = AppTheme.colors.background,
            contentColor = AppTheme.colors.fg
        ) { padding ->
            NavHost(
                modifier = Modifier.fillMaxSize(),
                navController = appConfig.navController,
                startDestination = appConfig.startDestination
            ) {
                appConfig.featureEntries.forEach { entry ->
                    entry.register(this, appConfig.navController, appConfig.navScope)
                }
            }
        }
    }
}