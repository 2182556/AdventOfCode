use std::collections::HashMap;

fn main() {
    let input = include_str!("input.txt");
    let instructions = input.lines().collect::<Vec<_>>()[0].chars().map(|c| {
        match c {
            'L' => 0,
            'R' => 1,
            _ => panic!("Invalid input"),
        }
    }).collect::<Vec<_>>();
    let mut nodes = HashMap::new();
    for line in input.lines().skip(2) {
        let mut iter = line.split("=");
        let node = iter.next().unwrap().trim();
        let mut network = iter.next().unwrap().split(",");
        let left = network.next().unwrap().chars().filter(|c| c.is_alphanumeric()).collect::<String>();
        let right = network.next().unwrap().chars().filter(|c| c.is_alphanumeric()).collect::<String>();
        nodes.insert(node.to_string(), (left, right));
    }
    part_one(&nodes, &instructions);
    part_two(&nodes, &instructions);
}

fn part_one(nodes: &HashMap<String, (String, String)>, instructions: &[usize]) {
    let mut number_of_steps = 0;
    let mut current_node = "AAA";
    loop {
        let (left, right) = nodes.get(current_node).unwrap();
        if instructions[number_of_steps % instructions.len()] == 0 {
            current_node = left;
        } else {
            current_node = right;
        }
        number_of_steps += 1;
        if current_node == "ZZZ" {
            break;
        }
    }
    println!("Part 1 - Number of steps: {}", number_of_steps);
}

fn gcd(a: usize, b: usize) -> usize {
    if b == 0 {
        return a;
    }
    gcd(b, a % b)
}

fn get_lcm(numbers: &[usize]) -> usize {
    let mut lcm = numbers[0];
    for i in 1..numbers.len() {
        lcm = lcm * numbers[i] / gcd(lcm, numbers[i]);
    }
    lcm
}

fn part_two(nodes: &HashMap<String, (String, String)>, instructions: &[usize]) {
    let starting_points = nodes.keys().clone().filter(|s| s.ends_with("A")).collect::<Vec<_>>();
    let mut possible_ending_points: HashMap<String, HashMap<String, Vec<usize>>> = HashMap::from(
        starting_points.clone().iter().map(|s| (s.to_string(), HashMap::new())).collect::<HashMap<_, _>>()
    );
    let mut possible_number_of_steps = Vec::new();
    for start in starting_points {
        let mut current_node = start;
        let mut number_of_steps = 0;
        let mut repeating_pattern = false;
        loop {
            let (left, right) = nodes.get(current_node).unwrap();
            if instructions[number_of_steps % instructions.len()] == 0 {
                current_node = left;
            } else {
                current_node = right;
            }
            number_of_steps += 1;
            
            if current_node.ends_with("Z") {
                if possible_ending_points.get(start).unwrap().contains_key(&current_node.to_string()) {
                    let steps = possible_ending_points.get(start).unwrap().get(&current_node.to_string()).unwrap();
                    for  step in steps.iter() {
                        if number_of_steps % instructions.len() == *step % instructions.len() {
                            possible_number_of_steps.push(*step);
                            repeating_pattern = true;
                            break;
                        }
                    }

                }
                possible_ending_points.get_mut(start).unwrap().entry(current_node.to_string()).or_insert(vec![number_of_steps]).push(number_of_steps);
            }
            if repeating_pattern {
                break;
            }
        }
    }
    println!("Part 2 - Number of steps: {}", get_lcm(&possible_number_of_steps));
}
