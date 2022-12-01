with open('input.txt') as f:
    calories = [0];
    for line in f:
        if line.strip():
            calories[len(calories)-1] += int(line);
        else:
            calories.append(0);
            
print('Solution part one: ', max(calories));            
print('Solution part two: ', sum(sorted(calories, reverse=True)[:3]));