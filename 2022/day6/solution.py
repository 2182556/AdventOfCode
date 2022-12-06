def isMarker(linePart):
    return len(set(linePart))==len(linePart);

with open('input.txt') as f:
    for l in f:
        for i in range(3, len(l)): 
            if isMarker(l[i-3:i+1]): print('Solution part one: ', i+1); break;     
        for i in range(13, len(l)):
            if isMarker(l[i-13:i+1]): print('Solution part two: ', i+1); break;