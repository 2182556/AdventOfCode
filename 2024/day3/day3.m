clc 
clearvars

inputFile = fopen('input.txt','r');
input = fscanf(inputFile,'%s',[1 Inf]);
fclose(inputFile);

lines = {strcat('do()', input)};

partTwo = true;
if partTwo
    commandoPattern = regexpPattern("(do\(\))((.|\n)*?)(don't\(\))");
    lines = extract(lines, commandoPattern);
end

multiplications = 0;
mulPattern = regexpPattern('mul[(][0-9]+,[0-9]+[)]');

for i = 1:length(lines)
    multiplication = extract(lines(i), mulPattern);
    if ~isempty(multiplication)
        for j = 1:length(multiplication)
            numbers = str2double(extract(multiplication{j,1}, digitsPattern));
            multiplications = multiplications + numbers(1) * numbers(2);
        end
    end
end

fprintf('Answer: %d\n',multiplications);