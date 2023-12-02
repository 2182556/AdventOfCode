import numpy as np
import time
from collections import deque

with open('input.txt') as f:
    lines = f.readlines()
    valves = {}
    valve_numeric = {}
    flow_rates = np.zeros(len(lines))
    start = None
    for i, line in enumerate(lines):
        valve = line.split()[1]
        if valve == "AA":
            start = i
        valve_numeric[valve] = i
    
    for i, line in enumerate(lines):
        parts = line.split()
        flow_rates[i] = int(parts[4].split('=')[1].replace(';', ''))
        valves[i] = np.array([valve_numeric[x.replace(',', '')] for x in parts[9:]])
    
    def next_action(pos_0, pos_1, prev_0, prev_1, open_valves, pressure, minute, highest_pressure):
        if minute >= 26:
            return pressure

        actions_0 = deque([x for x in valves[pos_0] if not x == prev_0])
        actions_1 = deque([x for x in valves[pos_1] if not x == prev_1])
        if not open_valves[pos_0]:
            actions_0.appendleft(-1)
        if not open_valves[pos_1] and pos_0 != pos_1:
            actions_1.appendleft(-1)
            
        if len(actions_0) == 0 or len(actions_1) == 0:
            # empty move
            return pressure
        
        for action_0 in actions_0:
            if action_0 == -1:
                next_pos_0 = pos_0
            else:
                next_pos_0 = action_0
                
            for action_1 in actions_1:
                if action_1 == -1:
                    next_pos_1 = pos_1
                else:
                    next_pos_1 = action_1
                
                copy = open_valves.copy()
                new_pressure = pressure
                if action_0 == -1:
                    copy[pos_0] = True
                    new_pressure  += flow_rates[pos_0] * (26 - minute)
                if action_1 == -1:
                    copy[pos_1] = True
                    new_pressure  += flow_rates[pos_1] * (26 - minute)
                if np.all(copy):
                    return new_pressure
                
                unopened = np.sort(flow_rates[copy == False])
                max_possible_pressure = new_pressure + sum([(26 - minute - i) * unopened[i:i+2].sum() for i in range(0, len(unopened), 2)])
                if max_possible_pressure > highest_pressure:
                    p = next_action(next_pos_0, next_pos_1, pos_0, pos_1, copy, new_pressure, minute + 1, highest_pressure)
                    highest_pressure = max(highest_pressure, p)

        return highest_pressure
    
    open_valves = flow_rates == 0
    start_time = time.time()
    highest_pressure= next_action(start, start, start, start, open_valves, 0, 1, 0)
    print('Time taken: ', time.time() - start_time)
    print('Solution part two: ', highest_pressure)