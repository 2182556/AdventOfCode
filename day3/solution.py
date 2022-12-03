prioritySum, prioritySumBadges = 0, 0;
def getPriority(c):
    if c.isupper(): return ord(c) - ord('A') + 27;                    
    else: return ord(c) - ord('a') + 1;
    
with open('input.txt') as f:
    for l in f:
        commons = [c for c in l[:len(l)//2] if c in l[len(l)//2:]]
        prioritySum += getPriority(commons[0]);
    f.seek(0);
    for l in f:
        bag2, bag3 = next(f), next(f);
        commons = [c for c in l if c in bag2 and c in bag3];  
        prioritySumBadges += getPriority(commons[0]);
            
print('Solution part one: ', prioritySum);
print('Solution part two: ', prioritySumBadges);