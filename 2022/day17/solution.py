import numpy as np

with open('2022/day17/input.txt') as f:
    # # print([jet for jet in jets if jet != '<' and jet != '>'])
    # instructions = [-1 if jet == '<' else 1 for jet in jets]
    # current_i = 0
    # # shapes = [[['#', '#', '#', '#']], [['.', '#', '.'], ['#', '#', '#'], ['.', '#', '.']], [['#', '#', '#'], ['.', '.', '#'], ['.', '.', '#']], [['#'], ['#'], ['#'], ['#']],[['#', '#'], ['#', '#']]]
    # shapes = [[[1, 1, 1, 1]], [[0, 1, 0], [1, 1, 1], [0, 1, 0]], [[1, 1, 1], [0, 0, 1], [0, 0, 1]], [[1], [1], [1], [1]],[[1, 1], [1, 1]]]
    # chamber_width = 7
    # field = []
    # # field = np.empty((0, chamber_width), int)
    # rock_locations = []
    
    # def get_next_instruction():
    #     global current_i
    #     if current_i >= len(instructions):
    #         current_i = 0
    #     current_i += 1
    #     return instructions[current_i - 1]
    
    
    # while total < 2022:
    #     if total % 100000 == 0: 
    #         print(total)
    #     rock = shapes[total % 5]
    #     x = (2, 2 + len(rock[0]) - 1)
    #     for _ in range(0,3):
    #         direction = get_next_instruction()
    #         x_ = (x[0] + direction, x[1] + direction)
    #         if x_[0] >= 0 and x_[1] < chamber_width: 
    #             x = x_
        
    #     y = (len(field), len(field) + len(rock) - 1)
    #     clashed_y = False
    #     while not clashed_y:
    #         direction = get_next_instruction()
    #         x_ = (x[0] + direction, x[1] + direction)
    #         clashed_x = False
    #         if x_[0] >= 0 and x_[1] < chamber_width: 
    #             for j, line in enumerate(rock):
    #                 if y[0] + j < len(field):
    #                     for k, c in enumerate(line):
    #                         if c == 1 and field[y[0] + j][x_[0] + k] == 1:
    #                             clashed_x = True
    #                             break
    #         else:
    #             clashed_x = True
                
    #         if not clashed_x:
    #             x = x_
            
    #         y_ = (y[0] - 1, y[1] - 1)
    #         if y_[0] >= 0:
    #             for j, line in enumerate(rock):
    #                 if y_[0] + j < len(field):
    #                     for k, c in enumerate(line):
    #                         if c == 1 and field[y_[0] + j][x[0] + k] == 1:
    #                             clashed_y = True
    #                             break
    #         else:
    #             clashed_y = True
                
    #         if not clashed_y:
    #                 y = y_
                
    #     if y[1] >= len(field):
    #         for _ in range(y[1] + 1 - len(field)):
    #             # field = np.append(field, [[0 for _ in range(7)]], axis=0)
    #             field.append([0 for _ in range(7)])
        
    #     for j, line in enumerate(rock):
    #         for k, c in enumerate(line):
    #             if c == 1:
    #                 field[y[0] + j][x[0] + k] = 1
        
    #     total += 1
    
    # jets = list(f.readlines()[0])
    # instructions = [-1 if jet == '<' else 1 for jet in jets]
    # current_i = 0
    # shapes = [[[1, 1, 1, 1]], [[0, 1, 0], [1, 1, 1], [0, 1, 0]], [[1, 1, 1], [0, 0, 1], [0, 0, 1]], [[1], [1], [1], [1]],[[1, 1], [1, 1]]]
    # # shapes = [[[True, True, True, True]], [[False, True, False], [True, True, True], [False, True, False]], [[True, True, True], [False, False, True], [False, False, True]], [[True], [True], [True], [True]],[[True, True], [True, True]]]
    # chamber_width = 7
    # # field = []
    # # field = np.empty((0, chamber_width), int)
    # rock_locations = set()
    
    # def get_next_instruction():
    #     global current_i
    #     if current_i >= len(instructions):
    #         current_i = 0
    #     current_i += 1
    #     return instructions[current_i - 1]
      
    # total = 0      
    # height = 0  
    # while total < 1000000000000:
    #     if total % 100000 == 0: 
    #         print(total)
    #     rock = shapes[total % 5]
    #     x = (2, 2 + len(rock[0]) - 1)
    #     for _ in range(0,3):
    #         direction = get_next_instruction()
    #         x_ = (x[0] + direction, x[1] + direction)
    #         if x_[0] >= 0 and x_[1] < chamber_width: 
    #             x = x_
        
    #     y = (height, height + len(rock) - 1)
    #     clashed_y = False
    #     while not clashed_y:
    #         direction = get_next_instruction()
    #         x_ = (x[0] + direction, x[1] + direction)
    #         clashed_x = False
    #         if x_[0] >= 0 and x_[1] < chamber_width: 
    #             for j, line in enumerate(rock):
    #                 if y[0] + j < height:
    #                     for k, c in enumerate(line):
    #                         if c == 1 and (y[0]+j, x_[0]+k) in rock_locations:
    #                             clashed_x = True
    #                             break
    #         else:
    #             clashed_x = True
                
    #         if not clashed_x:
    #             x = x_
            
    #         y_ = (y[0] - 1, y[1] - 1)
    #         if y_[0] >= 0:
    #             for j, line in enumerate(rock):
    #                 if y_[0] + j < height:
    #                     for k, c in enumerate(line):
    #                         if c == 1 and (y_[0] + j, x[0] + k) in rock_locations:
    #                             clashed_y = True
    #                             break
    #         else:
    #             clashed_y = True
                
    #         if not clashed_y:
    #                 y = y_
                
    #     if y[1] >= height:
    #         height = y[1] + 1
        
    #     for j, line in enumerate(rock):
    #         for k, c in enumerate(line):
    #             if c == 1 and (y[0] + j, x[0] + k) not in rock_locations:
    #                 rock_locations.add((y[0] + j, x[0] + k))
        
    #     total += 1
    jets = list(f.readlines()[0])
    instructions = [-1 if jet == '<' else 1 for jet in jets]
    current_i = 0
    # shapes = [[np.array([True, True, True, True])], [np.array([False, True, False]), np.array([True, True, True]), np.array([False, True, False])], \
    #     [np.array([True, True, True]), np.array([False, False, True]), np.array([False, False, True])], [np.array([True]), np.array([True]), np.array([True]), np.array([True])],\
    #         [np.array([True, True]), np.array([True, True])]]
    shapes = [[[True, True, True, True]], [[False, True, False], [True, True, True], [False, True, False]], [[True, True, True], [False, False, True], [False, False, True]], [[True], [True], [True], [True]],[[True, True], [True, True]]]
    chamber_width = 7
    field = []
    # field = np.empty((0, chamber_width), int)
    # rock_locations = {
    #     0: set(),
    #     1: set(),
    #     2: set(),
    #     3: set(),
    #     4: set(),
    #     5: set(),
    #     6: set(),
    # }
    height = 0 
    
    def get_next_instruction():
        global current_i
        current_i += 1
        return instructions[(current_i - 1) % len(instructions)]
    
    def get_clashed(x, y, rock):
        global field
        for j, line in enumerate(rock):
            if y + j < len(field):
                for k, c in enumerate(line):
                    if c and field[y + j][x + k]:
                        return True
        return False
      
    total = 0
    while total < 2022:
        if total % 100000 == 0: 
            print(total)
        rock = shapes[total % len(shapes)]
        x = 2
        for _ in range(0,3):
            direction = get_next_instruction()
            x_ = x + direction
            if x_ >= 0 and x_ + len(rock[0]) - 1 < chamber_width: 
                x = x_
                
        y = len(field)
        clashed_y = False
        while not clashed_y:
            direction = get_next_instruction()
            x_ = x + direction
            if x_ >= 0 and x_ + len(rock[0]) - 1 < chamber_width and not get_clashed(x_, y, rock):
                x = x_
            
            y_ = y - 1
            if y_ >= 0 and not get_clashed(x, y_, rock):
                y = y_
            else:
                clashed_y = True 
                
        if y + len(rock) - 1 >= len(field):
            for _ in range(y + len(rock) - len(field)):
                field.append([False for _ in range(7)])
        
        for j, line in enumerate(rock):
            for k, c in enumerate(line):
                if c:
                    field[y + j][x + k] = True
        
        total += 1
                
    field.reverse()
    field = [['#' if x else '.' for x in line] for line in field]
    print(*field, sep='\n')
    print(height)
    print(len(field))