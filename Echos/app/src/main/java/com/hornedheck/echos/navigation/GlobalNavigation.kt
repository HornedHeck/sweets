package com.hornedheck.echos.navigation

import ru.terrakok.cicerone.Cicerone

class GlobalNavigation {

    internal val cicerone = Cicerone.create()

    internal val router
        get() = cicerone.router

    internal val navHolder
        get() = cicerone.navigatorHolder

}