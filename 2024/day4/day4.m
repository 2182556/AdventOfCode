clc
clearvars

input = cell2mat(importdata('input.txt', ''));

function xmas = findXmas(input, location, direction, nextLetterIndex)
    letters = ['X', 'M', 'A', 'S'];
    xmas = false;
    nextLocation = [location(1) + direction(1), location(2) + direction(2)];
    if all(nextLocation > 0) && all(nextLocation <= length(input))
        if input(nextLocation(1),nextLocation(2)) == letters(nextLetterIndex)            
            if (nextLetterIndex < 4)
                xmas = findXmas(input, nextLocation, direction, nextLetterIndex + 1);
            else
                xmas = true;
            end
        end
    end
end

xmasCount = 0;
masCount = 0;
for i=1:length(input)
     for j=1:length(input(i, :))
         if input(i,j) == 'X'
             for x=-1:1
                for y=-1:1
                    if findXmas(input, [i,j], [x,y], 2)
                        xmasCount = xmasCount + 1;
                    end
                end
             end
         elseif input(i,j) == 'M'
             for x=[-1, 1]
                 for y=[-1, 1]
                     if findXmas(input, [i,j], [x,y], 3)
                         X_x = input(i + 2*x, j);
                         X_y = input(i, j + 2*y);
                         if (X_x == 'M' && X_y == 'S') || (X_x == 'S' && X_y == 'M')
                            masCount = masCount + 1;
                         end
                     end
                 end
             end
         end
     end
end

fprintf('Answer part one: %d\n',xmasCount);
fprintf('Answer part two: %d\n',masCount/2);