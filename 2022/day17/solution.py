with open('2022/day17/input.txt') as f:
    jets = list(f.readlines()[0])
    instructions = [-1 if jet == '<' else 1 for jet in jets]
    current_i = 0
    shapes = [[[True, True, True, True]], 
              [[False, True, False], [True, True, True], [False, True, False]], 
              [[True, True, True], [False, False, True], [False, False, True]], 
              [[True], [True], [True], [True]],
              [[True, True], [True, True]]]
    chamber_width = 7
    field = []
    number_of_rocks = {}
    rock_and_jet = {}
    
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
    
    tower_units = 0
    total = 1000000000000
    current = 0
    while current < total:
        rock = shapes[current % len(shapes)]
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
        
        current += 1
        number_of_rocks[len(field)] = current
        
        if current_i > len(instructions) and tower_units == 0:
            if (current % len(shapes), current_i % len(instructions)) in rock_and_jet:
                previous_height = rock_and_jet[(current % len(shapes), (current_i) % len(instructions))]
                len_pattern = len(field) - previous_height
                pattern = True
                for i in range(1, len_pattern + 1):
                    if field[-i] != field[-i - len_pattern]:
                        pattern = False
                        break
                if pattern and previous_height in number_of_rocks:
                    repeating_rocks = number_of_rocks[len(field)] - number_of_rocks[previous_height]
                    tower_units = ((total - current) // repeating_rocks) * len_pattern
                    key_list = [key for key, val in number_of_rocks.items() if val == (total - current + 1) % repeating_rocks]
                    if len(key_list) > 0:
                        tower_units += key_list[-1]
                        current = total
                    else:
                        current = total - (total - current) % repeating_rocks
                    
                    
        rock_and_jet[(current % len(shapes), (current_i) % len(instructions))] = len(field)
                
    # field.reverse()
    # field = [['#' if x else '.' for x in line] for line in field]
    # print(*field, sep='\n')
    print('Tower height: ', tower_units + len(field))