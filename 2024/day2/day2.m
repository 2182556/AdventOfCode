clc 
clearvars

inputFile = fopen('input.txt','r');
lines = textscan(inputFile,'%s', 'Whitespace','\n');
fclose(inputFile);
lines = lines{1};

count = 0;
additionalCount = 0;

function c = tolerationPolicy(line, index_difference, safeReportCondition)
    c = 0;
    toleratedLines = [[line(1:end ~= index_difference)]; [line(1:end ~= index_difference+1)]]';
    for toleratedLine = toleratedLines
        differencesToleratedLine = diff(toleratedLine);
        if all(safeReportCondition(differencesToleratedLine))
            c = 1;
            break
        end
    end
end

for i = 1:length(lines)
    line = str2num(lines{i});
    differences = diff(line);

    positiveCondition = @(x) x > 0 & x < 4;
    negativeCondition = @(x) x < 0 & x > -4;
    if all(positiveCondition(differences)) || all(negativeCondition(differences))
        count = count + 1;
    else
        validPositive = nnz(positiveCondition(differences));
        validNegative = nnz(negativeCondition(differences));
        
        if validPositive >= length(differences) - 2
            index_difference = find(differences <= 0 | differences >= 4, 1);
            additionalCount = additionalCount + tolerationPolicy(line, index_difference, positiveCondition);
        elseif validNegative >= length(differences) - 2
            index_difference = find(differences >= 0 | differences <= -4, 1);
            additionalCount = additionalCount + tolerationPolicy(line, index_difference, negativeCondition);
        end
    end
end

fprintf('Solution part one: %d\n',count);
fprintf('Solution part two: %d\n',count + additionalCount);