package org.afterlike.polaris.events

data class PlayerState(
    val x: Double,
    val y: Double,
    val z: Double,
    val yaw: Float,
    val pitch: Float,
    val onGround: Boolean,
    val isSprinting: Boolean,
    val isSneaking: Boolean
)
