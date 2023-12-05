use std::{ops::Range, vec};

fn main() {
    let input = include_str!("input.txt");
    let mut maps: Vec<Vec<(Range<i64>, i64)>> = Vec::new();
    let parts_in_input: Vec<_> = input.split("\r\n\r\n").collect();
    
    for map in &parts_in_input[1..] {
        maps.push(Vec::new());
        for line in &map.lines().collect::<Vec<_>>()[1..]{
            let mappings = line.split(" ").collect::<Vec<_>>();
            let destination_start = mappings[0].parse::<i64>().unwrap();
            let source_start = mappings[1].parse::<i64>().unwrap();
            let range = mappings[2].parse::<i64>().unwrap();
            maps.last_mut().unwrap().push((source_start..source_start+range, destination_start-source_start));
        }
    }
    let seeds = parts_in_input[0].split(": ").collect::<Vec<_>>()[1].split_whitespace().collect::<Vec<_>>().iter().map(|x| x.parse::<i64>().unwrap()).collect::<Vec<_>>();

    part_one(&seeds, &maps);
    part_two(seeds, maps);
}

fn part_one(seeds: &Vec<i64>, maps: &Vec<Vec<(Range<i64>, i64)>>) {
    let mut location_numbers = Vec::new();
    for seed in seeds {
        let mut number: i64 = *seed;
        for map in maps {
            for (range, offset) in map {
                if range.contains(&number) {
                    number += offset;
                    break;
                }
            }
        }
        location_numbers.push(number);
    } 
    println!("Part one - Lowest location number: {}", location_numbers.iter().min().unwrap());
}

fn get_next_ranges(ranges: &Vec<Range<i64>>, map: &Vec<(Range<i64>, i64)>) -> Vec<Range<i64>> {
    let mut ranges = ranges.clone();
    let mut next_ranges = Vec::new();
    // iterate over this until there are no more matches
    let mut matches = true;
    while matches && ranges.len() > 0 {
        matches = false;
        for (range, offset) in map {
            for i in 0..ranges.len() {
                if ranges[i].start < range.end && ranges[i].end > range.start {
                    matches = true;
                    let overlap = ranges[i].start.max(range.start)..ranges[i].end.min(range.end);
                    next_ranges.push(overlap.start+offset..overlap.end+offset);
                    if overlap.start > ranges[i].start {
                        ranges.push(ranges[i].start..overlap.start)
                    }
                    if overlap.end < ranges[i].end {
                        ranges.push(overlap.end..ranges[i].end)
                    }
                    ranges.remove(i);
                    break;
                }
            }
        }
    }
    if ranges.len() > 0 {
        next_ranges.append(&mut ranges);
    }
    return next_ranges;
}

fn part_two(seeds: Vec<i64>, maps: Vec<Vec<(Range<i64>, i64)>>) {
    let seed_ranges = seeds.chunks(2).map(|x| x[0]..(x[0]+x[1])).collect::<Vec<_>>();
    let mut location_numbers = Vec::new();
    for seed_range in seed_ranges {
        let mut ranges = vec![seed_range];
        for map in &maps {
            ranges = get_next_ranges(&ranges, map);
        }
        location_numbers.push(ranges.clone());
    }
    let mut lowest = None;
    for locations in location_numbers {
        for location in locations {
            if lowest == None || location.start < lowest.unwrap() {
                lowest = Some(location.start);
            }
        }
    }
    println!("Part two - Lowest location number: {}", lowest.unwrap());
}