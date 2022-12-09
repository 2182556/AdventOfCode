def moveTail(t, h):
    if(t[0] !=  h[0] and t[1] != h[1]):
        if(t[0] < h[0] - 1): 
            t[0] += 1;
            if (t[1] < h[1]): t[1] += 1;
            else: t[1] -= 1;            
        elif(t[0] > h[0] + 1): 
            t[0] -= 1;
            if (t[1] < h[1]): t[1] += 1;
            else: t[1] -= 1;
                        
        elif(t[1] < h[1] - 1): 
            t[1] += 1;
            if (t[0] < h[0]): t[0] += 1;
            else: t[0] -= 1;            
        elif(t[1] > h[1] + 1): 
            t[1] -= 1;
            if (t[0] < h[0]): t[0] += 1;
            else: t[0] -= 1;
    elif(t[0] >  h[0] + 1): t[0] -= 1;
    elif(t[0] <  h[0] - 1): t[0] += 1;
    elif(t[1] >  h[1] + 1): t[1] -= 1;
    elif(t[1] <  h[1] - 1): t[1] += 1;
    return t;

with open('input.txt') as f:
    TailPositions = [[0,0]];
    HeadPosition = [0,0]
    TailPosition = [0,0]
    
    Positions = [[0,0]]*9;
    TailPositions9 = [[0,0]]
    for l in f:
        move = l.split(' ');
        for i in range(int(move[1])):
            if (move[0] == 'U'): HeadPosition[0] += 1;                
            elif (move[0] == 'R'): HeadPosition[1] += 1;                
            elif (move[0] == 'L'): HeadPosition[1] -= 1;                
            elif (move[0] == 'D'): HeadPosition[0] -= 1;
            previous = HeadPosition.copy();
            for j in range(len(Positions)):
                Positions[j] = moveTail(Positions[j].copy(), previous);
                previous = Positions[j].copy();
            if(Positions[len(Positions)-1] not in TailPositions9): TailPositions9.append(Positions[j].copy());   
                            
            TailPosition = moveTail(TailPosition, HeadPosition);
            if (TailPosition not in TailPositions): TailPositions.append(TailPosition.copy());
                

    print('Solution part one: ', len(TailPositions));
    print('Solution part two', len(TailPositions9))
                 
            