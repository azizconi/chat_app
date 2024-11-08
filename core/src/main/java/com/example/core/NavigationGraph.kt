package com.example.core

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationGraph {
    @Serializable
    data object ChatGraph : NavigationGraph()
    @Serializable
    data object ProfileGraph : NavigationGraph()
    @Serializable
    data object AuthGraph : NavigationGraph()
    @Serializable
    data object EmptyGraph : NavigationGraph()
}