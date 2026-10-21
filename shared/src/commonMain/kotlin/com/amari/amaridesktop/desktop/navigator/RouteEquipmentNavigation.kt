package com.amari.amaridesktop.desktop.navigator

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object InicioRoot : NavKey

@Serializable
data object LoginRoot : NavKey


@Serializable
data object DetailEquipment : NavKey

@Serializable
data object GestionMantenimiento : NavKey

@Serializable
data object RegistrarMantenimiento : NavKey

@Serializable
data object NuevoProovedor : NavKey