import copy

with open('input.txt') as f:
    structure = []
    for l in f:
        lines = [x.split(',') for x in l.strip().split(' -> ')]
        structure.append([[int(y) for y in lines[i]] for i in range(len(lines))])
    maxX = max([max([int(x[0]) for x in structure[i]]) for i in range(len(structure))])
    minX = min([min([int(x[0]) for x in structure[i]]) for i in range(len(structure))])
    maxY = max([max([int(x[1]) for x in structure[i]]) for i in range(len(structure))])
    minY = min([min([int(x[1]) for x in structure[i]]) for i in range(len(structure))])

    field = [['.' for i in range(maxX - minX + 1)] for j in range(maxY + 1)]
    
    for i in range(len(structure)):
        for j in range(len(structure[i])-1):
            if (structure[i][j][0] == structure[i][j+1][0]):
                rangeStart = structure[i][j][1]
                rangeEnd = structure[i][j+1][1]
                if (structure[i][j][1] > structure[i][j+1][1]): 
                    rangeStart = structure[i][j+1][1]
                    rangeEnd = structure[i][j][1]
                for k in range(rangeStart, rangeEnd+1):
                    field[k][structure[i][j][0]-minX] = '#'
            else:
                rangeStart = structure[i][j][0]-minX
                rangeEnd = structure[i][j+1][0]-minX
                if (structure[i][j][0] > structure[i][j+1][0]):     
                    rangeStart = structure[i][j+1][0]-minX
                    rangeEnd = structure[i][j][0]-minX    
                for k in range(rangeStart, rangeEnd+1):
                    field[structure[i][j][1]][k] = '#'
    sandUnits = 0
    blocked = True
    startingPoint = 500-minX
    position = [0, startingPoint]
    field[position[0]][position[1]] = '+'
    
    fieldWithFloor = copy.deepcopy(field)
    fieldWithFloor.append(['.' for i in range(maxX - minX + 1)])
    fieldWithFloor.append(['#' for i in range(maxX - minX + 1)])
    
    # part one
    while blocked:
        if position[0] >= len(field) -1 or position[1] >= len(field[0])-1 or position[1] <= 0:
            blocked = False
            break              
        if (field[position[0]+1][position[1]] == '.'):
            position[0] += 1                
        elif (position[1] > 0 and field[position[0]+1][position[1]-1] == '.'): 
            position = [position[0]+1, position[1]-1]                
        elif (position[1] < len(field[0]) and field[position[0]+1][position[1]+1] == '.'):
            position = [position[0]+1, position[1]+1]
        else:
            field[position[0]][position[1]] = 'o'
            sandUnits += 1                    
            position = [0, 500-minX]
    print('Solution part one: ', sandUnits)
    # part two
    blocked = True
    position = [0, startingPoint]
    moreSandUnits = 0    
    while blocked:
        if position[1] >= len(fieldWithFloor[0])-1 or position[1] <= 0:
            for i in range(len(fieldWithFloor)):
                fieldWithFloor[i].insert(0, '.')
                fieldWithFloor[i].append('.')
            fieldWithFloor[len(fieldWithFloor)-1] = ['#' for i in range(len(fieldWithFloor[0]))]
            position[1] = position[1] + 1
            startingPoint = startingPoint + 1
        if (fieldWithFloor[position[0]+1][position[1]] == '.'):
            position[0] += 1
        elif (position[1] > 0 and fieldWithFloor[position[0]+1][position[1]-1] == '.'):
            position = [position[0]+1, position[1]-1]
        elif (position[1] < len(fieldWithFloor[0]) and fieldWithFloor[position[0]+1][position[1]+1] == '.'):
            position = [position[0]+1, position[1]+1]    
        else:
            moreSandUnits += 1 
            if (position[0] == 0):                         
                blocked = False
                break
            fieldWithFloor[position[0]][position[1]] = 'o'                  
            position = [0, startingPoint]
    print('Solution part two: ', moreSandUnits)
    
        


