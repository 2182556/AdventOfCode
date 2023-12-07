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
        let best_distance = distances[i];
        let mut better_distances = 0;
        for speed in 1..time-1 {
            let distance = (time - speed) * speed;
            if distance > best_distance {
                better_distances += 1;
            } 
        }
        multiplied *= better_distances;
    }
    println!("Part one - Multiplied: {}", multiplied);
}

fn part_two(input: &str) {
    let time: String = input.lines().collect::<Vec<_>>()[0].split(": ").collect::<Vec<_>>()[1].split_whitespace().collect();
    let distance: String = input.lines().collect::<Vec<_>>()[1].split(": ").collect::<Vec<_>>()[1].split_whitespace().collect();
    let time = time.parse::<i64>().unwrap();
    let best_distance = distance.parse::<i64>().unwrap();
    let mut range = 1..time-1;
    for speed in 1..time-1 {
        let distance = (time - speed) * speed;
        if distance > best_distance {
            range = speed..time-1;
            break;
        }
    }
    for speed in (1..time-1).rev() {
        let distance = (time - speed) * speed;
        if distance > best_distance {
            range = range.start..speed;
            break;
        }
    }
    println!("Part two - Number of ways: {:?}", range.end + 1 - range.start)
}