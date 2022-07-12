package utils.actions.State

data class State(
    val time: String,
    val action: String,
    val srcVertex: Long,
    val dstVertex: Long? = -1
)