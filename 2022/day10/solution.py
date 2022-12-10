X = 1;
cycle = 0;
wantedCycle = 20;
signalStrength = 0;
screenRow = 40;
instructions = [];
pixels = [[]];

def newCycle(x):
    global X, cycle, wantedCycle, signalStrength, instructions, pixels;
    pixels[len(pixels)-1].append('#' if abs(X - (cycle % screenRow)) < 2 else '.');
    cycle += 1; 
    if (cycle >= wantedCycle): signalStrength += X*wantedCycle; wantedCycle += 40;
    if (cycle % screenRow == 0): pixels.append([]); 
    
    instructions.append(x);
    X += instructions.pop(0);

with open('input.txt') as f:
    for l in f:
        if (l.strip()): newCycle(0);
        if (l.split(' ')[0] == 'addx'): newCycle(int(l.split(' ')[1]));
            
print('Solution part one: ', signalStrength)
print('Solution part two: ')
print(*[''.join(p) for p in pixels], sep='\n')