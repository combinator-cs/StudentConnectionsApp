package com.proj.services.user.api

class ConnectionGraph() {
    private val adjacencyMap = hashMapOf<String, ArrayList<String>>()

    fun createGraph(connections: List<Connection>): ConnectionGraph {
        for (connection in connections) {
            if (!adjacencyMap.containsKey(connection.userId1)) {
                adjacencyMap[connection.userId1] = arrayListOf()
            }
            if (!adjacencyMap.containsKey(connection.userId2)) {
                adjacencyMap[connection.userId2] = arrayListOf()
            }
            adjacencyMap[connection.userId1]?.add(connection.userId2)
            adjacencyMap[connection.userId2]?.add(connection.userId1)
        }
        return this
    }

    fun bfs(node: String): HashMap<String, Int> {
        val visited = hashMapOf<String, Int>()
        val queue = ArrayDeque<String>()
        visited[node] = 0
        queue.add(node)

        while (queue.isNotEmpty()) {
            val vertex = queue.removeFirst()
            for (adj in adjacencyMap[vertex] ?: emptyList()) {
                if (!visited.containsKey(adj)) {
                    visited[adj] = visited[vertex]?.plus(1) ?: 0
                    queue.add(adj)
                }
            }
        }
        return visited
    }
}