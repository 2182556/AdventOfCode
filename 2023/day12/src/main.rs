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
        println!("known locations ranges: {:?}", known_locations_ranges);
        println!("possible locations ranges: {:?}", possible_locations_ranges);
        println!("damaged springs: {:?}", contiguous_damaged_springs);
        let mut different_arrangements = 1;
        // let mut coinciding_ranges: Vec<Range<usize>> = known_locations_ranges.iter().filter(|x| possible_locations_ranges.contains(x)).map(|x| x.clone()).collect();
        // println!("coinciding ranges: {:?}", coinciding_ranges);
        // find possible ranges for each set of springs and check if there are any cases where ranges can be removed (also based on order)

        // let mut length_and_ranges: Vec<(i32, Vec<(usize, Range<usize>)>)> = Vec::new();
        // for contiguous_springs in contiguous_damaged_springs {
        //     let mut possible_ranges: Vec<(usize, Range<usize>)> = Vec::new();
        //     println!("length and ranges: {:?}", length_and_ranges);
        //     let mut tocheck = possible_locations_ranges.clone();
        //     if length_and_ranges.len() > 0 {
        //         tocheck = possible_locations_ranges[length_and_ranges[length_and_ranges.len()-1].1[0].0..].to_vec();
        //     }
        //     for (i, range) in tocheck.iter().enumerate() {
        //         if range.len() >= contiguous_springs as usize {
        //             possible_ranges.push((i, range.clone()));
        //         }
        //     }
        //     if (possible_ranges.len() == 1) && (possible_ranges[0].1.len() <= (contiguous_springs + 1).try_into().unwrap()) {
        //         println!("Removing range: {:?} ", possible_ranges[0].1);
        //         different_arrangements *= possible_ranges[0].1.len() as i32 - contiguous_springs;
        //         let index = possible_ranges[0].0;
        //         let mut cloned_ranges = possible_locations_ranges.clone();
        //         cloned_ranges.remove(index);
        //         possible_locations_ranges = cloned_ranges;
        //         for (_spring, ranges) in length_and_ranges.iter_mut() {
        //             ranges.retain(|(_, r)| r != &possible_ranges[0].1);
        //         }
        //     }
        //     else {

        //         length_and_ranges.push((contiguous_springs, possible_ranges));
        //     }
        // }
        // println!("length and ranges end: {:?}", length_and_ranges);

        // all in the same range -> range starts later for other springs
        // also counts for two with the same range, second one (if following) starts later in the range if the first one has that as first range as well
        // check if known locations are satisfied with found ranges
        // if any lenght of range in known locations matches the largest range, that also needs to be removed from the options
        
        let mut range_and_springs: Vec<(Range<usize>, Vec<i32>)> = Vec::new();
        for range in &possible_locations_ranges {
            range_and_springs.push((range.clone(), Vec::new()));
            for (i, spring) in contiguous_damaged_springs.iter().enumerate() {
                if *spring <= range.len() as i32 {
                    let previous_item = range_and_springs.len()-1;
                    range_and_springs[previous_item].1.push(i as i32);
                }
            }
        }
        println!("Possible locations ranges: {:?}", possible_locations_ranges);
    }
}
