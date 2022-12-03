scorePartOne, scorePartTwo = 0, 0;
# Rock, paper, scissors
# Part 2: lose, draw, win
points = [3, 0, 6];
with open('input.txt') as f:
    for line in f:
        opp = ord(line[0]) - ord('A') + 1;
        self = ord(line[2]) - ord('X') + 1;

        # part one
        scorePartOne += self + points[(opp - self) % 3];
            
        # part two
        if (self == 1): 
            if (opp > 1): scorePartTwo += opp - 1;
            else: scorePartTwo += 3;
        elif (self == 2): scorePartTwo += 3 + opp;
        else:
            scorePartTwo += 6;
            if (opp < 3): scorePartTwo += opp + 1;
            else: scorePartTwo += 1;
            
print("Solution part one: ", scorePartOne);
print("Solution part two: ", scorePartTwo);