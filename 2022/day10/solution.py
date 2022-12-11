X = 1
cycle = 0
signalStrength = 0
instructions = []
image = []

def newCycle(x):
    global X, cycle, signalStrength, instructions, image
    if (cycle % 40 == 0): image.append([])
    image[len(image)-1].append('#' if abs(X - (cycle % 40)) < 2 else '.')
    
    cycle += 1
    if ((cycle + 20) % 40 == 0): signalStrength += X*cycle
    
    instructions.append(x)
    X += instructions.pop(0)

with open('input.txt') as f:
    for l in f:
        if l.strip(): newCycle(0)
        if l.split(' ')[0] == 'addx': newCycle(int(l.split(' ')[1]))
            
print('Solution part one: ', signalStrength)
print('Solution part two: ', *[' '.join(p) for p in image], sep='\n')