package com.game.synergism.state

import com.game.synergism.core.Decimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


data class Player(
    var firstPlayed: String = LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'")),
    var worlds: QuarkHandler,
    var coins: Decimal,
    var coinsThisPrestige: Decimal,
    var coinsThisTranscension: Decimal,
    var coinsThisReincarnation: Decimal,
    var coinsTotal: Decimal,

    var firstOwnedCoin: Double = 0.0,
    var firstGeneratedCoin: Decimal,
    var firstCostCoin: Decimal,
    var firstProduceCoin: Double = 0.0,
)