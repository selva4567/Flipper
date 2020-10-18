package com.selvakumarsm.flipper.loan.domain.model

data class RepaymentModel(private val _amount: String, private val _tenure: String) {
    var amount : String = _amount
    private set
    public get() {
        return "\$ $_amount /mo"
    }

    var tenure : String = _tenure
    private set
    public get() {
        return "for $_tenure months"
    }
}