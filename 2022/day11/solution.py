import copy
with open('input.txt') as f:
    input = f.readlines();
    monkeys = [];
    items = [];
    operations = [];
    itemsdict = {};
    for i in range(0, len(input), 7):
        monkeys.append(0);
        items.append([int(x) for x in input[i+1].split(':')[1].strip().split(', ')])
        operations.append([input[i+2].split()[4], input[i+2].split()[5], int(input[i+3].split()[3]), [int(input[i+4].split()[5]), int(input[i+5].split()[5])]])

    for i in range(len(monkeys)):
        for item in items[i]:
            itemsdict[len(itemsdict)] = [item, i];
            
    allDivisions = 1;
    for i in range(len(operations)):
        allDivisions *= operations[i][2];
            
    def newNumber(op, old, value):
        if op == '+': return old + value
        else: return old * value
            
    def getMonkeyBusiness(monkeyBusiness, r, items, div, divisionNr):
        for k in range(r):
            for i in range(len(items)):
                item = items[i][0]
                j = items[i][1]
                while True:  
                    h = j;                
                    monkeyBusiness[j] += 1
                    number = operations[j][1];

                    if number == 'old': item = newNumber(operations[j][0], item, item)
                    else: item = newNumber(operations[j][0], item, int(number))
                    if div: item = int(item/divisionNr);
                    else: item = item % divisionNr;

                    if (item % operations[j][2] == 0): j = operations[j][3][0]
                    else: j = operations[j][3][1]
                    if (j <= h): break; 

                items[i] = [item, j]
        monkeyBusiness.sort(reverse=True)
        return monkeyBusiness[0]*monkeyBusiness[1]

    print('Solution part one: ', getMonkeyBusiness(copy.deepcopy(monkeys), 20, copy.deepcopy(itemsdict), True, 3))
    print('Solution part two: ', getMonkeyBusiness(copy.deepcopy(monkeys), 10000, copy.deepcopy(itemsdict), False, allDivisions))