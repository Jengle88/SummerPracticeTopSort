package utils

enum class GraphToolsState {
    WAITING,
    SET_VERTEX,
    REMOVE_VERTEX,
    SET_EDGE_FIRST,
    SET_EDGE_SECOND,
    REMOVE_EDGE_FIRST,
    REMOVE_EDGE_SECOND,
    PAUSE,
    CONTINUE,
    TO_BEGIN,
    TO_FINISH,
    STEP_BACK,
    STEP_NEXT
}