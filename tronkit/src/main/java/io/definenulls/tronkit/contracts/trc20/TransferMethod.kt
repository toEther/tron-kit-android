package io.definenulls.tronkit.contracts.trc20

import io.definenulls.tronkit.models.Address
import io.definenulls.tronkit.contracts.ContractMethod
import java.math.BigInteger

class TransferMethod(val to: Address, val value: BigInteger) : ContractMethod() {

    override val methodSignature = Companion.methodSignature
    override fun getArguments() = listOf(to, value)

    companion object {
        const val methodSignature = "transfer(address,uint256)"
    }

}
