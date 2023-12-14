# from mpl_toolkits import mplot3d
# import matplotlib.pyplot as plt
    
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
    
    
    highest_offset_x = max([cube[0] for cube in cubes]) + 1
    highest_offset_y = max([cube[1] for cube in cubes]) + 1
    highest_offset_z = max([cube[2] for cube in cubes]) + 1
    # print(highest_offset_x, highest_offset_y, highest_offset_z)
    
    field = [[[False for _ in range(highest_offset_z)] for _ in range(highest_offset_y)] for _ in range(highest_offset_x)]
    # print(field)
    for cube in cubes:
        # print(cube)
        field[cube[0]-1][cube[1]-1][cube[2]-1] = True

    for i in range(1, len(field) - 1):
        for j in range(1, len(field[0]) - 1):
            for k in range(1, len(field[0][0]) - 1):
                if not field[i][j][k]:
                    surrounded = [[False, False], [False, False], [False, False]]
                    surrounded[0][0] = field[i - 1][j][k]
                    surrounded[1][0] = field[i][j - 1][k]
                    surrounded[2][0] = field[i][j][k - 1]
                    for x in range(i, len(field)):
                        if field[x][j][k]:
                            surrounded[0][1] = True
                            break
                    surrounded[1][0] = field[i][j - 1][k]
                    for y in range(j, len(field[0])):
                        if field[i][y][k]:
                            surrounded[1][1] = True
                            break
                    surrounded[2][0] = field[i][j][k - 1]
                    for z in range(k, len(field[0][0])):
                        if field[i][j][z]:
                            surrounded[2][1] = True
                            break
                    
                    # print(surrounded)
                    if surrounded == [[True, True], [True, True], [True, True]]:
                        # print(i, j, k)
                        changed = True
                        field[i][j][k] = True
                        cubes.append([i, j, k])
    
                    
    # ax = plt.axes(projection='3d')
    # ax.set_xlim3d(0, highest_offset_x)
    # ax.set_ylim3d(0, highest_offset_y)
    # ax.set_zlim3d(0, highest_offset_z)
    # ax.scatter3D([cube[0] for cube in cubes], [cube[1] for cube in cubes], [cube[2] for cube in cubes], marker='s', s=100)
    # plt.show()
    
    exposed_sides = 6 * sum([sum([sum([1 for cube in column if cube]) for column in row]) for row in field])
    for i in range(len(field)):
        for j in range(len(field[0])):
            for k in range(len(field[0][0])):
                if field[i][j][k]:
                    if i < len(field) - 1 and field[i + 1][j][k]:
                        exposed_sides -= 2
                    if j < len(field[0]) - 1 and field[i][j + 1][k]:
                        exposed_sides -= 2
                    if k < len(field[0][0]) - 1 and field[i][j][k + 1]:
                        exposed_sides -= 2
                    
    print(exposed_sides)
                
    
   # 2074 too low
   # 2096 too high