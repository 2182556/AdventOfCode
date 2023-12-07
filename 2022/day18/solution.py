with open('2022/day18/input.txt') as f:
    cubes = list(f.readlines())
    cubes = [cube.strip().split(',') for cube in cubes]
    cubes = [[int(cube[0]), int(cube[1]), int(cube[2])] for cube in cubes]
    exposed_sides = 6 * len(cubes)
    for i, cube in enumerate(cubes):
        for j in range(i, len(cubes)):
            for x, y, z in [cube]:
                for x_, y_, z_ in [cubes[j]]:
                    if abs(x - x_) + abs(y - y_) + abs(z - z_) == 1:
                        exposed_sides -= 2
                        break
    print(exposed_sides)
    
    
    highest_offset = max([max(cube) for cube in cubes])
    print(highest_offset)
    
    for i, cube in enumerate(cubes):
        if x < highest_offset and y < highest_offset and z < highest_offset:
            pass
            # surrounded = [[False, False], [False, False], [False, False]]
            # for other_cube in cubes:
            #     if cube != other_cube:
            #         # print(cube)
            #         if cube[1:] == other_cube[1:]:
            #             if cube[0] > other_cube[0]: 
            #                 surrounded[0][0] = True
            #             elif cube[0] < other_cube[0]:
            #                 surrounded[0][1] = True
            #         if [cube[i] for i in [0,2]] == [other_cube[i] for i in [0,2]]:
            #             if cube[1] > other_cube[1]: 
            #                 surrounded[1][0] = True
            #             elif cube[1] < other_cube[1]:
            #                 surrounded[1][1] = True
            #         if cube[:2] == other_cube[:2]:
            #             if cube[2] > other_cube[2]: 
            #                 surrounded[2][0] = True
            #             elif cube[2] < other_cube[2]:
            #                 surrounded[2][1] = True   
            # if surrounded == [(True, True), (True, True), (True, True)]:
            #     print(cube)
            ## does not yield any results
            
            # for every side that does not have a neighbour, check if there are other cubes on the same axis and check diagonal adjacent 
            # 3D field wont get that big
   