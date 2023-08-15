package io.definenulls.tronkit.models

import io.definenulls.tronkit.decoration.TransactionDecoration

class FullTransaction(
    val transaction: Transaction,
    val decoration: TransactionDecoration
)
