package com.selvakumarsm.elasticmodule2

interface ElasticProperties {
    fun setStateChangeListener(stateChangeListener: StateChangeListener)
    fun expand()
    fun collapse()
    fun toggle()
}