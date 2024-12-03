fn main() {
    let input = include_str!("input.txt");
    let mut x_s: Vec<i64> = Vec::new();
    let mut y_s: Vec<i64> = Vec::new();
    for (i, line) in input.lines().enumerate() {
        for (j, c) in line.chars().enumerate() {
            if c == '#' {
                x_s.push(i as i64);
                y_s.push(j as i64);
            }
        }
    }
    let expansion_rate = 1000000; // 2 for part 1
    let mut row = 0;
    loop {
        if x_s.contains(&row) {
            row += 1;
            continue;
        }
        let mut all_smaller = true;
        for x in &mut x_s {
            if *x > row {
                *x += expansion_rate - 1;
                all_smaller = false;
            }
        }
        if all_smaller {
            break;
        }
        row += expansion_rate;
    }
    let mut column = 0; 
    loop {
        if y_s.contains(&column) {
            column += 1;
            continue;
        }
        let mut all_smaller = true;
        for y in &mut y_s {
            if *y > column {
                *y += expansion_rate - 1;
                all_smaller = false;
            }
        }
        if all_smaller {
            break;
        }
        column += expansion_rate;
    }
    let mut sum_of_paths: i64 = 0;
    for i in 0..x_s.len() {
        for j in i..x_s.len() {
            let x_diff = x_s[i] - x_s[j];
            let y_diff = y_s[i] - y_s[j];
            sum_of_paths += x_diff.abs() + y_diff.abs();
        }
    }
    println!("Sum of paths: {}", sum_of_paths)
}
