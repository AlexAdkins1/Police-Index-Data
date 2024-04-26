public class SolveMethod {
  public List<Configuration> solve(Configuration start){
  
          // BFS Setup
          Map<Configuration, Configuration> predecessors = new HashMap<>();
          LinkedList<Configuration> queue = new LinkedList<>();
  
          // Adds start to queue and map
          predecessors.put(start, start);
          queue.add(start);
  
          // Loops while there's neighbors to explore and the solution is not found
          while (!queue.isEmpty() && !queue.getFirst().isSolution()){
              Configuration current = queue.removeFirst();
              // If the current configuration is not the solution
              if (!current.isSolution()){
                  // Gets neighbors
                  for (Configuration neighbor : current.getNeighbors()){
                      // Increments number of configurations
                      this.totalConfigs++;
                      // Adds neighbor if it is not explored
                      if (!predecessors.containsKey(neighbor)){
                          queue.add(neighbor);
                          predecessors.put(neighbor, current);
                      }
                  }
              }
          }
  
          // Configurations are only added once as a key in the predecessor map
          this.uniqueConfigs = predecessors.size();
  
          // Constructs path
          List<Configuration> path = new LinkedList<>();
          if (!queue.isEmpty()){
              Configuration current = queue.removeFirst();
              while (!current.equals(start)) {
                  path.addFirst(current);
                  current = predecessors.get(current);
              }
              // Adds start to the path
              path.addFirst(start);
          }
  
          return path;
      }
}
