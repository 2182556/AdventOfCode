use std::{collections::HashMap, collections::HashSet, vec};

fn main() {
    let input = include_str!("input.txt");
    let mut coordinates: HashMap<(i32, i32), char> = HashMap::new();
    for (y, line) in input.lines().enumerate() {
        for (x, c) in line.chars().enumerate() {
            coordinates.insert((x as i32, y as i32), c);
        }
    }
    let field_size = (input.lines().next().unwrap().chars().count(), input.lines().count());
    let start = coordinates.iter().find_map(|(k, v)| if *v == 'S' { Some(k) } else { None }).unwrap();
    let mut current_coordinates: HashMap<char, Vec<(i32, i32)>> = HashMap::from([
        ('l', vec![]),
        ('r', vec![]),
        ('u', vec![]),
        ('d', vec![]),
    ]);
    let direction_symbols = HashMap::from(
        [
            ('l', vec!['S', '-', '7', 'J']),
            ('r', vec!['S', '-', 'F', 'L']),
            ('u', vec!['S', '|', 'L', 'J']),
            ('d', vec!['S', '|', 'F', '7']),
        ]
    );
    let connected_symbols = HashMap::from(
        [
            ('l', vec!['-', 'F', 'L']),
            ('r', vec!['-', '7', 'J']),
            ('u', vec!['|', 'F', '7']),
            ('d', vec!['|', 'L', 'J']),
        ]
    );
    if start.0 > 0 {
        let next_symbol = coordinates.get(&(start.0 - 1, start.1)).unwrap();
        if connected_symbols.get(&'l').unwrap().contains(next_symbol) {
            current_coordinates.get_mut(&'l').unwrap().push((start.0 - 1, start.1));
        }
    }
    if (start.0 as usize) < coordinates.len() - 1 {
        let next_symbol = coordinates.get(&(start.0 + 1, start.1)).unwrap();
        if connected_symbols.get(&'r').unwrap().contains(next_symbol) {
            current_coordinates.get_mut(&'r').unwrap().push((start.0 + 1, start.1));
        }
    }
    if start.1 > 0 {
        let next_symbol = coordinates.get(&(start.0, start.1 - 1)).unwrap();
        if connected_symbols.get(&'u').unwrap().contains(next_symbol) {
            current_coordinates.get_mut(&'u').unwrap().push((start.0, start.1 - 1));
        }
    }
    if (start.1 as usize) < coordinates.len() - 1 {
        let next_symbol = coordinates.get(&(start.0, start.1 + 1)).unwrap();
        if connected_symbols.get(&'d').unwrap().contains(next_symbol) {
            current_coordinates.get_mut(&'d').unwrap().push((start.0, start.1 + 1));
        }
    }
    let mut step = 1;
    let mut all_visited_coordinates: Vec<(i32, i32)> = vec![(start.0, start.1)];
    all_visited_coordinates.extend(current_coordinates.values().flatten());
    let first_coordinates = current_coordinates.clone();
    loop {
        let mut next_coordinates: HashMap<char, Vec<(i32, i32)>> = HashMap::from([
            ('l', vec![]),
            ('r', vec![]),
            ('u', vec![]),
            ('d', vec![]),
        ]);
        for (direction, list_of_coordinates) in current_coordinates.iter() {
            for (x, y) in list_of_coordinates.iter() {
                if x > &0 && direction != &'r' && direction_symbols.get(&'l').unwrap().contains(coordinates.get(&(*x, *y)).unwrap()) {
                    let next_symbol = coordinates.get(&(x - 1, *y)).unwrap();
                    if connected_symbols.get(&'l').unwrap().contains(next_symbol) {
                        next_coordinates.get_mut(&'l').unwrap().push((x - 1, *y));
                    }
                }
                if (*x as usize) < field_size.0 - 1 && direction != &'l' && direction_symbols.get(&'r').unwrap().contains(coordinates.get(&(*x, *y)).unwrap()) {
                    let next_symbol = coordinates.get(&(x + 1, *y)).unwrap();
                    if connected_symbols.get(&'r').unwrap().contains(next_symbol) {
                        next_coordinates.get_mut(&'r').unwrap().push((x + 1, *y));
                    }
                }
                if y > &0 && direction != &'d' && direction_symbols.get(&'u').unwrap().contains(coordinates.get(&(*x, *y)).unwrap()) {
                    let next_symbol = coordinates.get(&(*x, y - 1)).unwrap();
                    if connected_symbols.get(&'u').unwrap().contains(next_symbol) {
                        next_coordinates.get_mut(&'u').unwrap().push((*x, y - 1));
                    }
                }
                if (*y as usize) < field_size.1 - 1 && direction != &'u' && direction_symbols.get(&'d').unwrap().contains(coordinates.get(&(*x, *y)).unwrap()) {
                    let next_symbol = coordinates.get(&(*x, y + 1)).unwrap();
                    if connected_symbols.get(&'d').unwrap().contains(next_symbol) {
                        next_coordinates.get_mut(&'d').unwrap().push((*x, y + 1));
                    }
                }
            }
        }
        let flattened = next_coordinates.values().flatten().collect::<Vec<_>>();
        let has_duplicate_coordinates = flattened.len() != flattened.iter().collect::<HashSet<_>>().len();
        all_visited_coordinates.extend(flattened);
        step += 1;
        if has_duplicate_coordinates {
            println!("Part 1 - Steps: {}", step);
            break;
        }
        current_coordinates = next_coordinates;
    }

    let mut neighbouring_coordinates_1: Vec<(i32, i32)> = Vec::new();
    let mut neighbouring_coordinates_2: Vec<(i32, i32)> = Vec::new();
    let mut coordinate = ('l', (start.0, start.1));
    for (direction, coordinates) in first_coordinates.iter() {
        if coordinates.len() > 0 {
            coordinate = (*direction, coordinates[0]);
        }
    }

    loop {
        let current_character = coordinates.get(&coordinate.1).unwrap();
        if coordinate.0 == 'l' {
            if current_character == &'L' {
                let mut next = (coordinate.1 .0 - 1, coordinate.1 .1);
                while !all_visited_coordinates.contains(&next) && next.0 >= 0 {
                    neighbouring_coordinates_2.push(next);
                    next = (next.0 - 1, next.1);
                }
                next = (coordinate.1 .0, coordinate.1 .1 + 1);
                while !all_visited_coordinates.contains(&next) && next.1 < field_size.1 as i32 {
                    neighbouring_coordinates_2.push(next);
                    next = (next.0, next.1 + 1);
                }
            }
            else if current_character == &'F' {
                let mut next = (coordinate.1 .0 - 1, coordinate.1 .1);
                while !all_visited_coordinates.contains(&next) && next.0 >= 0 {
                    neighbouring_coordinates_1.push(next);
                    next = (next.0 - 1, next.1);
                }
                next = (coordinate.1 .0, coordinate.1 .1 - 1);
                while !all_visited_coordinates.contains(&next) && next.1 >= 0 {
                    neighbouring_coordinates_1.push(next);
                    next = (next.0, next.1 - 1);
                }
            }
            else if current_character == &'-' {
                let mut next = (coordinate.1 .0, coordinate.1 .1 - 1);
                while !all_visited_coordinates.contains(&next) && next.1 >= 0 {
                    neighbouring_coordinates_1.push(next);
                    next = (next.0, next.1 - 1);
                }
                next = (coordinate.1 .0, coordinate.1 .1 + 1);
                while !all_visited_coordinates.contains(&next) && next.1 < field_size.1 as i32{
                    neighbouring_coordinates_2.push(next);
                    next = (next.0, next.1 + 1);
                }
            }
        }
        else if coordinate.0 == 'r' {
            if current_character == &'J' {
                let mut next = (coordinate.1 .0 + 1, coordinate.1 .1);
                while !all_visited_coordinates.contains(&next) && next.0 < field_size.0 as i32 {
                    neighbouring_coordinates_1.push(next);
                    next = (next.0 + 1, next.1);
                }
                next = (coordinate.1 .0, coordinate.1 .1 + 1);
                while !all_visited_coordinates.contains(&next) && next.1 < field_size.1 as i32 {
                    neighbouring_coordinates_1.push(next);
                    next = (next.0, next.1 + 1);
                }
            }
            else if current_character == &'7' {
                let mut next = (coordinate.1 .0 + 1, coordinate.1 .1);
                while !all_visited_coordinates.contains(&next) && next.0 < field_size.0 as i32 {
                    neighbouring_coordinates_2.push(next);
                    next = (next.0 + 1, next.1);
                }
                next = (coordinate.1 .0, coordinate.1 .1 - 1);
                while !all_visited_coordinates.contains(&next) && next.1 >= 0 {
                    neighbouring_coordinates_2.push(next);
                    next = (next.0, next.1 - 1);
                }
            }
            else if current_character == &'-' {
                let mut next = (coordinate.1 .0, coordinate.1 .1 + 1);
                while !all_visited_coordinates.contains(&next) && next.1 < field_size.1 as i32 {
                    neighbouring_coordinates_1.push(next);
                    next = (next.0, next.1 + 1);
                }
                next = (coordinate.1 .0, coordinate.1 .1 - 1);
                while !all_visited_coordinates.contains(&next) && next.1 >= 0 {
                    neighbouring_coordinates_2.push(next);
                    next = (next.0, next.1 - 1);
                }
            }
        }
        else if coordinate.0 == 'u' {
            if current_character == &'7' {
                let mut next = (coordinate.1 .0 + 1, coordinate.1 .1);
                while !all_visited_coordinates.contains(&next) && next.0 < field_size.0 as i32 {
                    neighbouring_coordinates_1.push(next);
                    next = (next.0 + 1, next.1);
                }
                next = (coordinate.1 .0, coordinate.1 .1 - 1);
                while !all_visited_coordinates.contains(&next) && next.1 >= 0 {
                    neighbouring_coordinates_1.push(next);
                    next = (next.0, next.1 - 1);
                }
            }
            else if current_character == &'F' {
                let mut next = (coordinate.1 .0 - 1, coordinate.1 .1);
                while !all_visited_coordinates.contains(&next) && next.0 >= 0 {
                    neighbouring_coordinates_2.push(next);
                    next = (next.0 - 1, next.1);
                }
                next = (coordinate.1 .0, coordinate.1 .1 - 1);
                while !all_visited_coordinates.contains(&next) && next.1 >= 0 {
                    neighbouring_coordinates_2.push(next);
                    next = (next.0, next.1 - 1);
                }
            }
            else if current_character == &'|' {
                let mut next = (coordinate.1 .0 + 1, coordinate.1 .1);
                while !all_visited_coordinates.contains(&next) && next.0 < field_size.0 as i32 {
                    neighbouring_coordinates_1.push(next);
                    next = (next.0 + 1, next.1);
                }
                next = (coordinate.1 .0 - 1, coordinate.1 .1);
                while !all_visited_coordinates.contains(&next) && next.0 >= 0 {
                    neighbouring_coordinates_2.push(next);
                    next = (next.0 - 1, next.1);
                }
            }
        }
        else if coordinate.0 == 'd' {
            if current_character == &'J' || current_character == &'|' {
                let mut next = (coordinate.1 .0 + 1, coordinate.1 .1);
                while !all_visited_coordinates.contains(&next) && next.0 < field_size.0 as i32 {
                    neighbouring_coordinates_2.push(next);
                    next = (next.0 + 1, next.1);
                }
            }
            if current_character == &'L' || current_character == &'|' {
                let mut next = (coordinate.1 .0 - 1, coordinate.1 .1);
                while !all_visited_coordinates.contains(&next) && next.0 >= 0 {
                    neighbouring_coordinates_1.push(next);
                    next = (next.0 - 1, next.1);
                }
            }
            if current_character == &'J' {
                let mut next = (coordinate.1 .0, coordinate.1 .1 + 1);
                while !all_visited_coordinates.contains(&next) && next.1 < field_size.1 as i32 {
                    neighbouring_coordinates_2.push(next);
                    next = (next.0, next.1 + 1);
                }
            }
            if current_character == &'L' {
                let mut next = (coordinate.1 .0, coordinate.1 .1 + 1);
                while !all_visited_coordinates.contains(&next) && next.1 < field_size.1 as i32 {
                    neighbouring_coordinates_1.push(next);
                    next = (next.0, next.1 + 1);
                }
            }
        }
        if (coordinate.0 == 'l' && current_character == &'L') || (coordinate.0 == 'r' && current_character == &'J') || (coordinate.0 == 'u' && current_character == &'|'){
            coordinate = ('u', (coordinate.1 .0, coordinate.1 .1 - 1));
        }
        else if (coordinate.0 == 'l' && current_character == &'F') || (coordinate.0 == 'r' && current_character == &'7') || (coordinate.0 == 'd' && current_character == &'|') {
            coordinate = ('d', (coordinate.1 .0, coordinate.1 .1 + 1));
        }
        else if (coordinate.0 == 'u' && current_character == &'F') || (coordinate.0 == 'd' && current_character == &'L') || (coordinate.0 == 'r' && current_character == &'-') {
            coordinate = ('r', (coordinate.1 .0 + 1, coordinate.1 .1));
        }
        else if (coordinate.0 == 'u' && current_character == &'7') || (coordinate.0 == 'd' && current_character == &'J') || (coordinate.0 == 'l' && current_character == &'-') {
            coordinate = ('l', (coordinate.1 .0 - 1, coordinate.1 .1));
        }

        if coordinate.1 == *start {
            break;
        }
    }

    let mut inside_loop = 0;
    for (x, y) in neighbouring_coordinates_1.iter() {
        if x == &0 || y == &0 || x == &(field_size.0 as i32 - 1) || y == &(field_size.1 as i32 - 1) {
            inside_loop += 2;
            break;
        }
    }
    for (x, y) in neighbouring_coordinates_2.iter() {
        if x == &0 || y == &0 || x == &(field_size.0 as i32 - 1) || y == &(field_size.1 as i32 - 1) {
            inside_loop += 1;
            break;
        }
    }
    if inside_loop == 1 {
        print!("Part 2 - Enclosed tiles: {}", neighbouring_coordinates_1.iter().collect::<HashSet<_>>().len());
    }
    else if inside_loop == 2 {
        print!("Part 2 - Enclosed tiles: {}", neighbouring_coordinates_2.iter().collect::<HashSet<_>>().len());
    }
    else {
        panic!("Both loops reach border values");
    }

}
