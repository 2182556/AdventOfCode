import ast
with open('input.txt') as f:
    input = [];
    for l in f:
        if (l.strip()):
            input.append(ast.literal_eval(l.strip()))
    
    def compare(p1, p2):
        if (type(p1) == list):
            if (type(p2) == list): 
                if (p1 == p2): return 0
                for i in range(0, len(p1)):
                    if len(p2) > i:
                        if p1[i] != p2[i]:
                            comparison = compare(p1[i], p2[i])
                            if type(comparison) == bool: return comparison
                    else: return False
                return len(p1) < len(p2)
            else: return compare(p1, [p2])
        else:
            if (type(p2) == list): return compare([p1], p2)
            elif p1 == p2: return 0
            else: return p1 < p2
                
    sum = 0;
    for i in range(0, len(input), 2):
        if (compare(input[i], input[i+1])): sum += int(i / 2) + 1
        
    print('Solution part one: ', sum)
    
    input.append([2])
    input.append([6])
    
    loops = 0
    while loops < len(input):
        for i in range(loops, len(input)):
            if not compare(input[loops], input[i]):
                input[loops], input[i] = input[i], input[loops]
        loops += 1
        
    decoderKey = 1;
    for i in range(len(input)):
        if (input[i] == [2] or input[i] == [6]): decoderKey *= i + 1
            
    print('Solution part two: ', decoderKey)
            
            
        