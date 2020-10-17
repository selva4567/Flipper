package com.selvakumarsm.elasticmodule2

/**
 * Any view that supports collapse/expand feature, should implement this interface.
 */
interface ElasticProperties {
    /**
     * Setting up the listener to listen for the view state changes. Mostly listening on
     * before/after collapse and before/after expansion fo the view
     */
    fun setStateChangeListener(elasticStateChangeListener: ElasticStateChangeListener)

    /**
     * Returns current state of the view (i.e) EXPANDED/COLLAPSED
     */
    fun getCurrentElasticState() : State

    /**
     * On invoking this method, view should expand
     */
    fun expand()

    /**
     * On invoking this method, view should collapse
     */
    fun collapse()

    /**
     * This method should change the state of view from collapsed to expanded or vice-versa based on
     * the current state.
     */
    fun toggle()


    /**
     * Different state supported by the view.
     *
     * @property IN_TRANSITION is not used as of now.
     */
    enum class State {
        COLLAPSED, EXPANDED, IN_TRANSITION
    }
}

