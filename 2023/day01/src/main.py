import os
current_dir = os.path.dirname(os.path.abspath(__file__))
with open(os.path.join(current_dir, 'input.txt')) as f:
    with open(os.path.join(current_dir, 'results_rust.txt')) as r:
        lines = f.readlines()
        results = r.readlines()
        numbers = {
            "one": "1",
            "two": "2",
            "three": "3",
            "four": "4",
            "five": "5",
            "six": "6",
            "seven": "7",
            "eight": "8",
            "nine": "9",
        }
        calibration = 0
        i = 0
        for line in lines:
            first_index = len(line)-1
            first = line[first_index]
            last_index = 0
            last = line[last_index]
            for number_key, number in zip(numbers.keys(), numbers.values()):
                if number_key in line:
                    if line.index(number_key) < first_index:
                        first_index = line.index(number_key)
                        first = numbers[number_key]
                    if line.rindex(number_key) > last_index:
                        last_index = line.rindex(number_key)
                        last = numbers[number_key]
                if number in line:
                    if line.index(number) < first_index:
                        first_index = line.index(number)
                        first = number
                    if line.rindex(number) > last_index:
                        last_index = line.rindex(number)
                        last = number
            # print(f"{first}{last}")
            if f"{first}{last}" != results[i].strip():
                print(f"{i}: {first}{last} != {results[i].strip()}")
            calibration += int(first + last)
            i += 1
        print(calibration)