package com.inumaki.core.ui.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute

@Serializable
@SerialName("Discover")
data object DiscoverRoute: AppRoute

@Serializable
@SerialName("Repo")
data object RepoRoute: AppRoute

@Serializable
@SerialName("Home")
data object HomeRoute: AppRoute
