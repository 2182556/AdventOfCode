fn main() {
    let input = include_str!("input.txt");
    part_one(input);
    part_two(input);
}

fn part_one(input: &str) {
    let times: Vec<i32> = input.lines().collect::<Vec<_>>()[0].split(": ").collect::<Vec<_>>()[1].split_whitespace().collect::<Vec<_>>().iter().map(|x| x.parse::<i32>().unwrap()).collect();
    let distances: Vec<i32> = input.lines().collect::<Vec<_>>()[1].split(": ").collect::<Vec<_>>()[1].split_whitespace().collect::<Vec<_>>().iter().map(|x| x.parse::<i32>().unwrap()).collect();
    let mut multiplied = 1;
    for (i, time) in times.iter().enumerate() {
        for speed in 1..time-1 {
            if (time - speed) * speed > distances[i] {
                multiplied *= time - speed + 1 - speed;
                break;
            }
        }
    }
    println!("Part one - Multiplied: {}", multiplied);
}

fn part_two(input: &str) {
    let time: i64 = input.lines().collect::<Vec<_>>()[0].split(": ").collect::<Vec<_>>()[1].split_whitespace().collect::<String>().parse::<i64>().unwrap();
    let best_distance: i64 = input.lines().collect::<Vec<_>>()[1].split(": ").collect::<Vec<_>>()[1].split_whitespace().collect::<String>().parse::<i64>().unwrap();

    for speed in 1..time-1 {
        if (time - speed) * speed > best_distance {
            println!("Part two - Number of ways: {:?}", time - speed + 1 - speed);
            break;
        }
    }
}