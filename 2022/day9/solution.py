def moveTail(t, h):
    if (abs(t[0]-h[0]) <= 1 and abs(t[1]-h[1]) <= 1): return t
    if(t[0] > h[0]): t[0] -= 1
    if(t[0] < h[0]): t[0] += 1
    if(t[1] > h[1]): t[1] -= 1
    if(t[1] < h[1]): t[1] += 1
    return t

with open('input.txt') as f:
    tailPositions = [[0,0]]
    headPosition = [0,0]
    tailPosition = [0,0]
    
    positions = [[0,0]]*9
    tailPositions9 = [[0,0]]
    for l in f:
        move = l.split(' ')
        for i in range(int(move[1])):
            if (move[0] == 'U'): headPosition[0] += 1
            elif (move[0] == 'R'): headPosition[1] += 1
            elif (move[0] == 'D'): headPosition[0] -= 1
            elif (move[0] == 'L'): headPosition[1] -= 1
                        
            previous = headPosition.copy()
            for j in range(len(positions)):
                positions[j] = moveTail(positions[j].copy(), previous)
                previous = positions[j].copy()
                
            if(positions[0] not in tailPositions): tailPositions.append(positions[0].copy())
            if(positions[len(positions)-1] not in tailPositions9): tailPositions9.append(positions[j].copy())

    print('Solution part one: ', len(tailPositions))
    print('Solution part two: ', len(tailPositions9))