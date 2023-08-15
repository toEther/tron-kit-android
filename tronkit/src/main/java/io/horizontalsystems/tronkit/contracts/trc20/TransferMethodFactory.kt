package io.definenulls.tronkit.contracts.trc20

import io.definenulls.tronkit.models.Address
import io.definenulls.tronkit.contracts.ContractMethodFactory
import io.definenulls.tronkit.contracts.ContractMethodHelper
import io.definenulls.tronkit.toBigInteger

object TransferMethodFactory : ContractMethodFactory {

    override val methodId = ContractMethodHelper.getMethodId(TransferMethod.methodSignature)

    override fun createMethod(inputArguments: ByteArray): TransferMethod {
        val address = Address.fromRawWithoutPrefix(inputArguments.copyOfRange(12, 32))
        val value = inputArguments.copyOfRange(32, 64).toBigInteger()

        return TransferMethod(address, value)
    }

}
