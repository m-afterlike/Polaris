package org.afterlike.polaris.events

object EventManager {
    private val listeners = mutableListOf<Any>()

    fun register(obj: Any) {
        listeners.add(obj)
    }

    fun unregister(obj: Any) {
        listeners.remove(obj)
    }

    fun call(event: Any) {
        for (listener in listeners) {
            listener.javaClass.methods
                .filter { it.isAnnotationPresent(Subscribe::class.java) }
                .filter { it.parameterTypes.size == 1 && it.parameterTypes[0].isAssignableFrom(event.javaClass) }
                .forEach { it.invoke(listener, event) }
        }
    }
}