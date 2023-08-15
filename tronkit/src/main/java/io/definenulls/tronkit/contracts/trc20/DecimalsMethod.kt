package io.definenulls.tronkit.contracts.trc20

import io.definenulls.tronkit.contracts.ContractMethod


class DecimalsMethod: ContractMethod() {
    override var methodSignature = "decimals()"
    override fun getArguments() = listOf<Any>()
}
