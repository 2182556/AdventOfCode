def firstMarker(l, markerLength):
    return next(i for i in range(markerLength, len(l)) if len(set(l[i-markerLength:i])) == markerLength);

with open('input.txt') as f:
    l = f.read().strip();
    startOfPacket, startOfMessage = 4, 14;
    print('Solution part one: ', firstMarker(l, startOfPacket));
    print('Solution part two: ', firstMarker(l, startOfMessage));