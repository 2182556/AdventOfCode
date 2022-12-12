import copy

with open('input.txt') as f:
    heightMap = [];
    for l in f:
        heightMap.append([x for x in l.strip()])
    start = [0,0]
    end = [0,0]
    for i in range(len(heightMap)):
        for j in range(len(heightMap[0])):
            if heightMap[i][j] == 'S': start = [i,j]; heightMap[i][j] = 'a'
            if heightMap[i][j] == 'E': end = [i,j]; heightMap[i][j] = 'z'
    directions = [(0,1), (1,0), (0,-1), (-1,0)]
    
    def getShortestPath(q, field, reverse):
        while len(q) > 0:
            p = q.pop(0);
            for d in directions:
                if (p[0] + d[0] >= 0 and p[0] + d[0] < len(heightMap) and p[1] + d[1] >= 0 and p[1] + d[1] < len(heightMap[0])):
                    currentLetter = heightMap[p[0]][p[1]]
                    nextLetter = heightMap[p[0] + d[0]][p[1] + d[1]]
                    if (not reverse and ord(nextLetter) < ord(currentLetter) + 2) or (reverse and ord(currentLetter) < ord(nextLetter) + 2):
                        current = field[p[0]][p[1]];
                        next = field[p[0] + d[0]][p[1] + d[1]];
                        if next == 0 or current + 1 < next: 
                            field[p[0] + d[0]][p[1] + d[1]] = current + 1
                            q.append([p[0] + d[0], p[1] + d[1]])
        return field
    
    shortestPath = [[0 for i in range(len(heightMap[0]))] for j in range(len(heightMap))]
    print('Solution part one: ', getShortestPath([start], copy.deepcopy(shortestPath), False)[end[0]][end[1]])
    shortestPathA = getShortestPath([end], copy.deepcopy(shortestPath), True)
    a = 0
    for i in range(len(heightMap)):
        for j in range(len(heightMap[0])):
            if (heightMap[i][j]=='a'):
                if (shortestPathA[i][j] is not 0 and (a==0 or shortestPathA[i][j] < a)): a = shortestPathA[i][j]
    print('Solution part two: ', a)