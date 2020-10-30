package com.hornedheck.echos.navigation

import ru.terrakok.cicerone.Cicerone

class LocalNavigation {

    internal val cicerone = Cicerone.create()

    internal val router
        get() = cicerone.router

    internal val navHolder
        get() = cicerone.navigatorHolder

}