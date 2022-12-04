import re;
def inBetween(value, min, max):
    return value >= min and value <= max;

with open('input.txt') as f:
    sections = [[int(r) for r in re.split('-|,|-|\n', s)[:4]] for s in f.readlines()];
    print('Solution part one: ', sum([1 for n in sections if (n[0] <= n[2] and n[1] >= n[3]) or (n[0] >= n[2] and n[1] <= n[3])]));
    print('Solution part two: ', sum([1 for n in sections if inBetween(n[0], n[2], n[3]) or inBetween(n[1], n[2], n[3]) or inBetween(n[2], n[0], n[1]) or inBetween(n[3], n[0], n[1])]));
