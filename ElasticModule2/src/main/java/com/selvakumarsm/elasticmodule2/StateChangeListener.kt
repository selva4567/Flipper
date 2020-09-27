package com.selvakumarsm.elasticmodule2

interface StateChangeListener {
    fun postCollapse(view: ElasticView)
    fun postExpand(view: ElasticView)
    fun preCollapse(view: ElasticView)
    fun preExpand(view: ElasticView)
}