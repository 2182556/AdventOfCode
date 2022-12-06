def isMarker(linePart):
    return len(set(linePart))==len(linePart);

with open('input.txt') as f:
    for l in f:
        for c in range(3, len(l)): 
            if isMarker(l[c-3:c+1]): print('Solution part one: ', c+1); break;     
        for c in range(13, len(l)):
            if isMarker(l[c-13:c+1]): print('Solution part two: ', c+1); break;