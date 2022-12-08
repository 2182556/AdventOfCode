import numpy as np
with open('input.txt') as f:
    field = np.array([[int(n) for n in l.strip()] for l in f])
    visible = np.array([[False]*len(field[0]) for i in range(len(field))]);
    highestScenicScore = 0;
    for i in range(len(field)):
        for j in range(len(field[0])):
            if (i==0 or j==0 or i==len(field)-1 or j==len(field[0])-1): visible[i,j] = True;
            else:
                if (all(x < field[i,j] for x in field[i,:j]) or all(x < field[i,j] for x in field[i,j+1:]) or all(x < field[i,j] for x in field[:i,j]) or all(x < field[i,j] for x in field[i+1:,j])): visible[i,j] = True;
                
                viewingDistance = 1;
                for k in range(i-1, -1, -1):
                    if (field[k,j] >= field[i,j] or k == 0): viewingDistance *= (i-k); break;
                for k in range(i+1, len(field)):
                    if (field[k,j] >= field[i,j] or k == len(field)-1): viewingDistance *= (k-i); break;
                for k in range(j-1, -1, -1):
                    if (field[i,k] >= field[i,j] or k == 0): viewingDistance *= (j-k); break;
                for k in range(j+1, len(field[0])):
                    if (field[i,k] >= field[i,j] or k == len(field[0])-1): viewingDistance *= (k-j); break;
                
                if (viewingDistance > highestScenicScore): highestScenicScore = viewingDistance;
            
        
    print('Solution part one: ', sum(sum(1 for x in list if x) for list in visible));
    print('Solution part two: ', highestScenicScore);