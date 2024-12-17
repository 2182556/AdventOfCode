clc
clearvars

inputFile = fopen('input.txt','r');
input = textscan(inputFile, '%s', 'Delimiter', '\n');
fclose(inputFile);

lines = input{1};
splitIdx = find(cellfun(@isempty, lines));

inputRules = lines(1:splitIdx-1);
rulesList = cellfun(@(x) sscanf(x, '%d|%d')', inputRules, 'UniformOutput', false);
rulesList = cell2mat(rulesList);

inputPages = lines(splitIdx+1:end);
pages = cellfun(@(x) str2num(x), inputPages, 'UniformOutput', false);

keys = rulesList(:, 1);
values = rulesList(:, 2);

rules = containers.Map('KeyType', 'int32', 'ValueType', 'any');
for i = 1:length(keys)
    key = keys(i);
    value = values(i);
    if isKey(rules, key)
        rules(key) = [rules(key), value];
    else
        rules(key) = value;
    end
end

function newPage = validPage(pages, rules, i)
    valid = false;
    while ~valid
        valid = true;
        for j = 1:length(pages{i})
            if isKey(rules, pages{i}(j))
                rulesAfter = rules(pages{i}(j));
                numbersBefore = pages{i}(1:j-1);
                overlap = ismember(numbersBefore, rulesAfter);
                if any(overlap)
                    valid = false;
                    idx = find(overlap, 1, "first");
                    pages{i}([j, idx]) = pages{i}([idx, j]);
                    break
                end
            end
        end
    end
    newPage = pages{i};
end

validMiddlePageNumberSum = 0;
correctedMiddlePageNumberSum = 0;
for i = 1:length(pages)
    newPage = validPage(pages, rules, i);
    if newPage == pages{i}
        validMiddlePageNumberSum = validMiddlePageNumberSum + newPage(ceil(length(newPage)/2));
    else
        correctedMiddlePageNumberSum = correctedMiddlePageNumberSum + newPage(ceil(length(newPage)/2));
    end
end

disp("Answer part 1: " + validMiddlePageNumberSum);
disp("Answer part 2: " + correctedMiddlePageNumberSum);