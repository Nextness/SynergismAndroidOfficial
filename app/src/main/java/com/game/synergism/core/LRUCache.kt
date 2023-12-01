/**
  * LRUCache.kt
  * This is a re-implementation of lru-cache.ts in break_eternity from Patashu, available at
  * https://github.com/Patashu/break_eternity.js
  **/

package com.game.synergism.core

class ListNode<K, V>(val key: K, val value: V) {
    var next: ListNode<K, V>? = null
    var prev: ListNode<K, V>? = null
}

class LRUCache<K, V>(private val maxSize: Double) {
    private var map = mutableMapOf<K, ListNode<K, V>>()
    private var first: ListNode<K, V>? = null
    private var last: ListNode<K, V>? = null

    var size: Int = this.map.size

    fun get(key: K): V? {
        val node = this.map[key] ?: return null
        if (node != this.first) {
            if (node == this.last) {
                this.last = node.prev
                this.last?.next = null
            } else {
                node.prev?.next = node.next
                node.next?.prev = node.prev
            }
            node.next = this.first
            this.first?.prev = node
            this.first = node
        }
        return node.value
    }

    fun set(key: K, value: V) {
        if (this.maxSize < 1) {
            return
        }
        if (this.map.values.any { it == key }) {
            throw Error("Cannot update existing keys in the cache")
        }
        val node = ListNode(key, value)
        if (this.first == null) {
            this.first = node
            this.last = node
        } else {
            node.next = this.first
            this.first?.prev = node
            this.first = node
        }
        this.map[key] = node
        while (this.map.size > this.maxSize) {
            val last = this.last
            this.map.remove(last?.key)
            this.last = last?.prev
            this.last?.next = null
        }
    }
}