package com.hornedheck.firerx3

sealed class ChildAction<T>(val data: T)

class ChildAdd<T>(data: T) : ChildAction<T>(data)

class ChildUpdate<T>(data: T) : ChildAction<T>(data)

class ChildDelete<T>(data: T) : ChildAction<T>(data)