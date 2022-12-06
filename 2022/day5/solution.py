import re
import copy

with open('input.txt') as f:
    setup = first = True;
    crates, cratesCopy = [], [];
    for l in f:
        if first:
            first = False;
            crates = [[] for _ in range(int(len(l)/4))];
        if setup:
            if l[1] == '1': 
                setup = False;
                cratesCopy = copy.deepcopy(crates);
            else: 
                for i in range(len(crates)):
                    if l[i*4+1] is not ' ': crates[i].insert(0,l[i*4+1]);
        else:            
            c = re.split('move | from | to |\n', l)[1:4];
            if (len(c) > 2):
                for i in range(0, int(c[0])):
                    crates[int(c[2])-1].append(crates[int(c[1])-1].pop());
                    
                cratesCopy[int(c[2])-1].extend(cratesCopy[int(c[1])-1][-int(c[0]):]);
                del cratesCopy[int(c[1])-1][-int(c[0]):];
        
    print('Solution part one: ', ''.join([crate[-1] for crate in crates]));
    print('Solution part two: ', ''.join([crate[-1] for crate in cratesCopy]));
    