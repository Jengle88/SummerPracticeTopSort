package actions.State

class State(private val time: String,
private val action: String,
private val srcVertex: Long,
private val dstVertex: Long? = -1) {
}