import re
import numpy as np
import copy
crates = np.empty((9,),dtype=object)
with open('input.txt') as f:
    lines = f.readlines();
    for i in range(7, -1, -1):
        for j in range(1, len(lines[i])-1, 4):
            if crates[int(j/4)] is None: crates[int(j/4)] = [];
            if lines[i][j] != ' ': crates[int(j/4)].append(lines[i][j]);

    commands = [re.split('move | from | to |\n', s)[1:4] for s in lines[10:]];
    crates2 = copy.deepcopy(crates);
    for c in commands:
        for i in range(0, int(c[0]), 1):
            crate = crates[int(c[1])-1];
            crates[int(c[2])-1].append(crate.pop());
            
        crates2[int(c[2])-1].extend(crates2[int(c[1])-1][-int(c[0]):]);
        crates2[int(c[1])-1] = crates2[int(c[1])-1][:-int(c[0])];
        
    print('Solution part one: ', ''.join([crate.pop() for crate in crates]));
    print('Solution part two: ', ''.join([crate.pop() for crate in crates2]));
    