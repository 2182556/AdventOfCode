use std::collections::HashMap;

fn main() {
    let input = include_str!("input.txt");
    part_one(input);
    part_two(input);
}

fn part_one(input: &str) {
    let bag = HashMap::from([("red", 12), ("green", 13), ("blue", 14)]);
    let mut sum_ids = 0;
    for line in input.lines() {
        let mut possible = true;
        let game = line.split(":").collect::<Vec<_>>();
        let game_id: i32 = game[0].split(" ").collect::<Vec<_>>()[1].parse().unwrap();
        let sets = game[1].split(";").collect::<Vec<_>>();
        for set in sets {
            let set = set.split(",").collect::<Vec<_>>();
            for item in set {
                let item = item.trim().split(" ").collect::<Vec<_>>();
                let color = item[1];
                let count: i32 = item[0].parse().unwrap();
                if count > bag[color] {
                    possible = false;
                    break;
                }  
            }
            if !possible {
                break;
            }
        }
        if possible {
            sum_ids += game_id;
        }
    }
    println!("Part one - Sum of possible games' IDs: {}", sum_ids)
}

fn part_two(input: &str) {
    let mut sum_power = 0;
    for line in input.lines() {
        let mut minimum_set = HashMap::from([("red", 0), ("green", 0), ("blue", 0)]);

        let game = line.split(":").collect::<Vec<_>>();
        let sets = game[1].split(";").collect::<Vec<_>>();
        for set in sets {
            let set = set.split(",").collect::<Vec<_>>();
            for item in set {
                let item = item.trim().split(" ").collect::<Vec<_>>();
                let color = item[1];
                let count: i32 = item[0].parse().unwrap();
                if count > minimum_set[color] {
                    minimum_set.insert(color, count);
                }
            }
        }
        sum_power += minimum_set["red"] * minimum_set["green"] * minimum_set["blue"];
    }
    println!("Part two - Sum of powers: {}", sum_power)
}