package com.selvakumarsm.elasticmodule2

/**
 * Callbacks for notifying view state change (i.e) expanded to collapse or vice-versa
 */
interface ElasticStateChangeListener {
    //TODO - Remove the view arguments. Those are redundant.
    /**
     * Should be called after view is fully collapsed
     */
    fun postCollapse(view: ElasticView)

    /**
     * Should be called after view is fully expanded
     */
    fun postExpand(view: ElasticView)

    /**
     * Should be called just before view starts collapsing
     */
    fun preCollapse(view: ElasticView)

    /**
     * Should be called just before view starts expanding
     */
    fun preExpand(view: ElasticView)
}