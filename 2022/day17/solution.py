from collections import deque
with open('2022/day17/input.txt') as f:
    instructions = deque([-1 if c == '<' else 1 for c in list(f.readlines()[0])])
    shapes = [[['#', '#', '#', '#']], [['.', '#', '.'], ['#', '#', '#'], ['.', '#', '.']], [['.', '.', '#'], ['.', '.', '#'], ['#', '#', '#']], [['#'], ['#'], ['#'], ['#']],[['#', '#'], ['#', '#']]]
    field = [['.' for _ in range(7)] for _ in range(3)]
    for i in range(0, 4, 4):
        for rock in shapes:
            # move until reaching something at the bottom
            for j in range(3): field.append(['.' for _ in range(7)]) # number of rows without objects
            for l, line in enumerate(rock):
                field[len(field)-1-l][2:2+len(line)] = line
    print(*field, sep='\n')