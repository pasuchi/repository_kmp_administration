package com.amari.amaridesktop.desktop.navigator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.NavKey

class Navigator(start: NavKey) {
    var backStack: List<NavKey> by mutableStateOf(listOf(start))
        private set

    fun navigate(key: NavKey) {
        backStack = backStack + key
    }

    fun pop() {
        if (backStack.size > 1) {
            backStack = backStack.dropLast(1)
        }
    }

    fun replaceWith(key: NavKey) {
        backStack = backStack.dropLast(1) + key
    }
}