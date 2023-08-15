package io.definenulls.tronkit.contracts.trc20

import io.definenulls.tronkit.contracts.ContractMethod


class NameMethod: ContractMethod() {
    override var methodSignature = "name()"
    override fun getArguments() = listOf<Any>()
}
