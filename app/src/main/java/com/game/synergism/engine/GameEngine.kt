package com.game.synergism.engine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


class GameEngine(scope: CoroutineScope) {
    private val mutex: Mutex = Mutex()
    private val gameLoopInMillis: Long = 200
    private var totalTime: Double = 0.0
    private var elapsedTime: Double = 0.0

    init {
        scope.launch(context = scope.coroutineContext) {
            while (true) {
                delay(gameLoopInMillis)
                mutex.withLock {

                }
                totalTime += (gameLoopInMillis / 1000) as Double
                elapsedTime += (gameLoopInMillis / 1000) as Double
            }
        }
    }
}
