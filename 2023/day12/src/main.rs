use std::ops::Range;

fn main() {
    let input = include_str!("input.txt");
    let mut sum_different_arrangements = 0;
    for line in input.lines() {
        let parts: Vec<&str> = line.split(" ").collect();
        let contiguous_damaged_springs: Vec<i32> = parts[1].split(",").map(|x| x.parse::<i32>().unwrap()).collect();
        let springs: Vec<char> = parts[0].chars().collect();
        let mut known_locations: Vec<usize> = Vec::new();
        let mut possible_locations: Vec<usize> = Vec::new();
        for (i, spring) in springs.iter().enumerate() {
            if *spring == '#' {
                known_locations.push(i);
                possible_locations.push(i);
            }
            if *spring == '?' {
                possible_locations.push(i);
            }
        }

        let mut known_locations_ranges: Vec<Range<usize>> = Vec::new();
        let mut possible_locations_ranges: Vec<Range<usize>> = Vec::new();
        let mut i = 0;
        while i < known_locations.len() {
            let start = known_locations[i];
            loop {
                if i+1 >= known_locations.len() {
                    break;
                }
                if known_locations[i+1] == known_locations[i]+1 {
                    i += 1;
                }
                else {
                    break;
                }
            }
            known_locations_ranges.push(start..known_locations[i]+1);
            i += 1;
        }
        i = 0;
        while i < possible_locations.len() {
            let start = possible_locations[i];
            loop {
                if i+1 >= possible_locations.len() {
                    break;
                }
                if possible_locations[i+1] == possible_locations[i]+1 {
                    i += 1;
                }
                else {
                    break;
                }
            }
            possible_locations_ranges.push(start..possible_locations[i]+1);
            i += 1;
        }
        // println!("known locations ranges: {:?}", known_locations_ranges);
        // println!("possible locations ranges: {:?}", possible_locations_ranges);
        println!("damaged springs: {:?}", contiguous_damaged_springs);
        let mut different_arrangements = 1;
        let mut coinciding_ranges: Vec<Range<usize>> = known_locations_ranges.iter().filter(|x| possible_locations_ranges.contains(x)).map(|x| x.clone()).collect();
        println!("coinciding ranges: {:?}", coinciding_ranges);
        // find possible ranges for each set of springs and check if there are any cases where ranges can be removed (also based on order)
        for contiguous_springs in contiguous_damaged_springs {
            let mut possible_ranges = Vec::new();
            for range in &possible_locations_ranges {
                if range.len() >= contiguous_springs as usize {
                    possible_ranges.push(range);
                }
            }
            println!("possible ranges: {:?}", possible_ranges);
            if possible_ranges.len() == 1 {

            }
        }
    }
}
