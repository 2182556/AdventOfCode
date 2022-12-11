import re;

with open('input.txt') as f:
    sections = [[int(r) for r in re.split('-|,|-|\n', s)[:4]] for s in f.readlines()];
    print('Solution part one: ', sum([1 for n in sections if (n[0] <= n[2] and n[1] >= n[3]) or (n[0] >= n[2] and n[1] <= n[3])]));
    print('Solution part two: ', sum([1 for n in sections if n[0] <= n[3] and n[1] >= n[2]]));
