package com.hornedheck.echos.base

import moxy.MvpPresenter

abstract class BasePresenter<V : BaseView> : MvpPresenter<V>()