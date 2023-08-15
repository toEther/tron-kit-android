package io.definenulls.tronkit.contracts.trc20

import io.definenulls.tronkit.contracts.ContractMethod


class SymbolMethod: ContractMethod() {
    override var methodSignature = "symbol()"
    override fun getArguments() = listOf<Any>()
}
