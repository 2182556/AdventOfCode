def getPriority(c):
    return ord(c) - ord('A') + 27 if c.isupper() else ord(c) - ord('a') + 1;
    
with open('input.txt') as f:
    bags = f.readlines();
    print('Solution part one: ', sum([getPriority([c for c in bag[:len(bag)//2] if c in bag[len(bag)//2:]][0]) for bag in bags]));
    print('Solution part two: ', sum([getPriority([c for c in bags[i] if c in bags[i+1] and c in bags[i+2]][0]) for i in range(0, len(bags), 3)]));