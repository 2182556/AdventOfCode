clc
clearvars

fileID = fopen('input.txt','r');
formatSpec = '%d %d\n';
input = fscanf(fileID,formatSpec,[2 Inf]);
fclose(fileID);

columns = input';
distances_summed = sum(abs(diff(sort(columns)')));
fprintf('Solution part one: %d\n',distances_summed);

count = 0;
for i = 1:length(columns)
    count = count + columns(i, 1) * sum(columns(:,2) == columns(i,1));
end

fprintf('Solution part two: %d\n',count);