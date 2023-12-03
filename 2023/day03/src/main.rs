use std::collections::HashMap;

fn main() {
    let input = include_str!("input.txt");
    let mut field = vec![vec!['.'; input.lines().nth(0).unwrap().len()]; input.lines().count()];
    for (i, line) in input.lines().enumerate(){
        for (j, c) in line.chars().enumerate(){
            field[i][j] = c;
        }
    }   
    part_one(&field);
    part_two(&field);
}

fn part_one(field: &Vec<Vec<char>>) {
    let mut sum_part_numbers = 0;
    for (i, line) in field.iter().enumerate(){
        let mut it = line.iter();
        let mut j = 0;
        while let Some(c) = it.next() {
            if c.is_numeric() {
                let mut number = c.to_string().to_owned();
                loop {
                    let next_c = it.next();
                    if next_c != None && next_c.unwrap().is_numeric() {
                        number.push(*next_c.unwrap());
                    } else {
                        break;
                    }
                }

                let k = j + number.len();
                let number = number.parse::<i32>().unwrap();
                let mut surrounding_chars = String::new();
                let l_start = if j > 0 { j - 1 } else { 0 };
                let l_end = if k < field[i].len()-1 { k + 1 } else { field[i].len() };
                let m_start = if i > 0 { i - 1 } else { 0 };
                let m_end = if i < field.len()-1 { i + 2 } else { field.len() };

                for l in l_start..l_end {
                    for m in m_start..m_end {
                        let item = field[m][l];
                        if !item.is_numeric() {
                            surrounding_chars.push(item);
                        }
                    }
                }
                
                let all_dots = surrounding_chars.chars().all(|c| c == '.');
                if !all_dots {
                    sum_part_numbers += number;
                }
                j = k;
            }
            j += 1;
        }
    }
    print!("Sum of part numbers: {}\n", sum_part_numbers);
}

fn part_two(field: &Vec<Vec<char>>) {
    let mut sum_gear_ratios = 0;
    let mut gears = HashMap::new();
    for (i, line) in field.iter().enumerate() {
        for (j, c) in line.iter().enumerate() {
            if *c == '*'{
                gears.insert((i, j), vec![]);
            }
        }
    }
    for (i, line) in field.iter().enumerate(){
        let mut it = line.iter();
        let mut j = 0;
        while let Some(c) = it.next() {
            if c.is_numeric() {
                let mut number = c.to_string().to_owned();
                loop {
                    let next_c = it.next();
                    if next_c != None && next_c.unwrap().is_numeric() {
                        number.push(*next_c.unwrap());
                    } else {
                        break;
                    }
                }

                let k = j + number.len();
                let number = number.parse::<i32>().unwrap();
                let l_start = if j > 0 { j - 1 } else { 0 };
                let l_end = if k < field[i].len()-1 { k + 1 } else { field[i].len() };
                let m_start = if i > 0 { i - 1 } else { 0 };
                let m_end = if i < field.len()-1 { i + 2 } else { field.len() };

                for l in l_start..l_end {
                    for m in m_start..m_end {
                        let item = field[m][l];
                        if item == '*' {
                            let adjacent = gears.get_mut(&(m, l)).unwrap();
                            adjacent.push(number);
                        }
                    }
                }
                j = k;
            }
            j += 1;
        }
    }
    for adjacent in gears.values() {
        if adjacent.len() == 2 {
            sum_gear_ratios += adjacent[0] * adjacent[1];
        }
    }
    print!("Sum of gear ratios: {}\n", sum_gear_ratios);
}