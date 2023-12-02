use aho_corasick::{AhoCorasick, MatchKind};
use std::collections::HashMap;

fn main() {
    let input = include_str!("input.txt");
    part_one(input);
    part_two(input);
}

fn part_one(input: &str) {
    let mut calibration: i32 = 0;
    for line in input.lines() {
        let calibration_values: Vec<char> = line.chars().filter(|c| c.is_digit(10)).collect();

        if calibration_values.len() > 0 {
            let number = format!("{}{}", calibration_values[0], calibration_values[calibration_values.len() - 1]);
            calibration += number.parse::<i32>().unwrap();
        }
    }
    print!("Calibration part one: {}\n", calibration)
}

fn part_two(input: &str) {
    let mut calibration: i32 = 0;
    let numbers: HashMap<&str, &str> = HashMap::from([("one", "1"), ("two", "2"), ("three", "3"), ("four", "4"), ("five", "5"), ("six", "6"), ("seven", "7"), ("eight", "8"), ("nine", "9")]);
    let all = numbers.keys().chain(numbers.values()).collect::<Vec<_>>();
    // let ac = AhoCorasick::new(all.clone()).unwrap();
    let ac = AhoCorasick::builder()
        .match_kind(MatchKind::Standard)
        .build(all.clone()).unwrap();

    for line in input.lines() {
        let mut matches = vec![];
        for mat in ac.find_overlapping_iter(line) {
            matches.push((mat.pattern(), mat.start(), mat.end()));
        }
        let mut calibration_value = "".to_owned();
        if let Some(min_match) = matches.iter().min_by_key(|&(_, start, _)| start) {
            let num = all[min_match.0.as_usize()];
            if numbers.contains_key(num) {
                calibration_value.push_str(numbers[num])
            } else {
                calibration_value.push_str(num)
            }
        }
        if let Some(max_match) = matches.iter().max_by_key(|&(_, _, end)| end) {
            let num = all[max_match.0.as_usize()];
            if numbers.contains_key(num) {
                calibration_value.push_str(numbers[num])
            } else {
                calibration_value.push_str(num)
            }
        }
        calibration += calibration_value.parse::<i32>().unwrap();
        
    }
    print!("Calibration part two: {}\n", calibration)
}
